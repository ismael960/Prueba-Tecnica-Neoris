package neoris.account.infraestructure.adapters.out.sql.service;

import jakarta.transaction.Transactional;
import neoris.account.infraestructure.adapters.out.sql.entity.AccountEntity;
import neoris.account.infraestructure.adapters.out.sql.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository clientRepository) {
        this.accountRepository = clientRepository;
    }

    public CompletableFuture<AccountEntity> save(AccountEntity accountEntity) {
        return CompletableFuture.supplyAsync(() -> accountRepository.save(accountEntity));
    }

    public CompletableFuture<Optional<AccountEntity>> findById(Long id) {
        return CompletableFuture.supplyAsync(() -> accountRepository.findById(id));
    }

    public CompletableFuture<AccountEntity> findByAccountNumber(String accountNumber) {
        return CompletableFuture.supplyAsync(() -> accountRepository.findByAccountNumber(accountNumber));
    }

    public CompletableFuture<Boolean> delete(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            accountRepository.deleteById(id);
            return true;
        });
    }

    public CompletableFuture<List<AccountEntity>> findAll() {
        return CompletableFuture.supplyAsync(accountRepository::findAll);
    }

    public CompletableFuture<List<AccountEntity>> findByClientId(Long clientId) {
        return CompletableFuture.supplyAsync(() -> accountRepository.findByClientId(clientId));
    }
}
