package neoris.account.infraestructure.adapters.out.sql;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import neoris.account.application.ports.out.AccountOutPort;
import neoris.account.domain.Account;
import neoris.account.infraestructure.adapters.out.sql.entity.AccountEntity;
import neoris.account.infraestructure.adapters.out.sql.mapper.AccountEntityMapper;
import neoris.account.infraestructure.adapters.out.sql.service.AccountService;
import neoris.movements.infraestructure.exception.AccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AccountSqlAdapter implements AccountOutPort {

    private final AccountEntityMapper accountEntityMapper;
    private final AccountService accountService;

    @Autowired
    public AccountSqlAdapter(AccountEntityMapper accountEntityMapper,
                             AccountService accountService) {
        this.accountEntityMapper = accountEntityMapper;
        this.accountService = accountService;
    }

    @Override
    public Mono<Account> save(Account account) {
        return Mono.fromCompletionStage(() -> {
                    AccountEntity clientEntity = accountEntityMapper.toAccountEntity(account);
                    clientEntity.setCreatedDate(LocalDateTime.now());
                    return accountService.save(clientEntity);
                })
                .doOnSuccess(clientEntity -> log.info("Account saved: " + clientEntity))
                .doOnError(throwable -> log.error("Error saving account: " + throwable))
                .map(accountEntityMapper::toAccount);
    }

    @Override
    public Mono<Account> update(Account account, Long id) {
        return Mono.fromCompletionStage(() -> {
                    Optional<AccountEntity> clientEntity = accountService.findById(id).join();
                    if (clientEntity.isPresent()) {
                        clientEntity.get().setAccountNumber(account.getAccountNumber());
                        clientEntity.get().setAccountType(account.getAccountType());
                        clientEntity.get().setBalance(account.getBalance());
                        clientEntity.get().setStatus(account.getStatus());
                        clientEntity.get().setModifiedDate(LocalDateTime.now());
                        clientEntity.get().setClientId(account.getClientId());
                        return accountService.save(clientEntity.get());
                    }
                    throw new AccountException("Account not found");
                })
                .doOnSuccess(clientEntity -> log.info("Account updated: " + clientEntity))
                .doOnError(throwable -> log.error("Error updating account: " + throwable))
                .map(accountEntityMapper::toAccount);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return Mono.fromCompletionStage(accountService.delete(id))
                .doOnSuccess(aBoolean -> log.info("Account deleted: " + aBoolean))
                .doOnError(throwable -> log.error("Error deleting account: " + throwable));
    }

    @Override
    public Mono<List<Account>> findAll() {
        return Mono.fromCompletionStage(accountService.findAll())
                .doOnSuccess(clientEntities -> log.info("Accounts found: " + clientEntities))
                .doOnError(throwable -> log.error("Error finding accounts: " + throwable))
                .map(accountEntityMapper::toAccounts);
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return Mono.fromCompletionStage(() -> accountService.findByAccountNumber(accountNumber))
                .doOnSuccess(clientEntity -> log.info("Account found: " + clientEntity))
                .doOnError(throwable -> log.error("Error finding account: " + throwable))
                .map(accountEntityMapper::toAccount);
    }

    @Override
    public Mono<List<Account>> findAccountsByClient(Long clientId) {
        return Mono.fromCompletionStage(() -> accountService.findByClientId(clientId))
                .doOnSuccess(clientEntities -> log.info("Accounts found: " + clientEntities))
                .doOnError(throwable -> log.error("Error finding accounts: " + throwable))
                .map(accountEntityMapper::toAccounts);
    }
}
