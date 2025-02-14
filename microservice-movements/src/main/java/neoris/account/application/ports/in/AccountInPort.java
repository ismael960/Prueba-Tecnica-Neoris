package neoris.account.application.ports.in;

import neoris.account.domain.Account;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountInPort {

    Mono<Account> save(Account account);

    Mono<Account> update(Account account, Long id);

    Mono<Boolean> delete(Long id);

    Mono<List<Account>> findAll();

    Mono<Account> findByAccountNumber(String accountNumber);

    Mono<List<Account>> findAccountsByClient(Long clientId);

}
