package neoris.account.application.usecase;

import lombok.extern.slf4j.Slf4j;
import neoris.account.application.ports.in.AccountInPort;
import neoris.account.application.ports.out.AccountOutPort;
import neoris.account.domain.Account;
import neoris.microservices.msclient.ClientConsumerOutPort;
import neoris.microservices.msclient.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@Service
public class AccountUseCase implements AccountInPort {

    private final AccountOutPort accountOutPort;
    private final ClientConsumerOutPort clientConsumerOutPort;

    @Autowired
    public AccountUseCase(AccountOutPort accountOutPort,
                          ClientConsumerOutPort clientConsumerOutPort) {
        this.accountOutPort = accountOutPort;
        this.clientConsumerOutPort = clientConsumerOutPort;
    }

    @Override
    public Mono<Account> save(Account client) {
        return accountOutPort.save(client);
    }

    @Override
    public Mono<Account> update(Account client, Long id) {
        return accountOutPort.update(client, id);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return accountOutPort.delete(id);
    }

    @Override
    public Mono<List<Account>> findAll() {

        return accountOutPort.findAll()
                .flatMap(accounts -> {
                    for (Account account : accounts) {
                        Client client = clientConsumerOutPort.getById(account.getClientId()).block();
                        account.setName(client.getName());
                    }
                    return Mono.just(accounts);
                });
    }

    @Override
    public Mono<Account> findByAccountNumber(String accountNumber) {
        return accountOutPort.findByAccountNumber(accountNumber);
    }

    @Override
    public Mono<List<Account>> findAccountsByClient(Long clientId) {
        return accountOutPort.findAccountsByClient(clientId);
    }
}
