package neoris.movements.application.usecase;

import neoris.account.application.ports.out.AccountOutPort;
import neoris.account.domain.Account;
import neoris.microservices.msclient.ClientConsumerOutPort;
import neoris.microservices.msclient.domain.Client;
import neoris.movements.application.ports.in.AccountStatementInPort;
import neoris.movements.application.ports.out.MovementOutPort;
import neoris.movements.domain.AccountStatementResponse;
import neoris.movements.domain.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountStatementUseCase implements AccountStatementInPort {

    private final MovementOutPort movementOutPort;
    private final AccountOutPort accountOutPort;
    private final ClientConsumerOutPort clientConsumerOutPort;

    @Autowired
    public AccountStatementUseCase(MovementOutPort movementOutPort,
                                   AccountOutPort accountOutPort,
                                   ClientConsumerOutPort clientConsumerOutPort) {
        this.movementOutPort = movementOutPort;
        this.accountOutPort = accountOutPort;
        this.clientConsumerOutPort = clientConsumerOutPort;
    }

    @Override
    public Mono<List<AccountStatementResponse>> getAccountStatement(String startDate, String endDate, Long clientId) {
        return accountOutPort.findAccountsByClient(clientId)
                .flatMap(accounts -> {
                    List<AccountStatementResponse> accountStatementResponses = new ArrayList<>();
                    for (Account account : accounts) {
                        Client client = clientConsumerOutPort.getById(account.getClientId()).block();
                        List<Movement> movements = movementOutPort.getAccountStatement(startDate, endDate, account.getId()).block();
                        if (movements != null) {
                            for (Movement movement : movements) {
                                AccountStatementResponse accountStatementResponse = getAccountStatementResponse(account, movement, client);
                                accountStatementResponses.add(accountStatementResponse);
                            }
                        }
                    }
                    return Mono.just(accountStatementResponses);
                });
    }

    private static AccountStatementResponse getAccountStatementResponse(Account account, Movement movement, Client client) {
        AccountStatementResponse accountStatementResponse = new AccountStatementResponse();
        accountStatementResponse.setDate(movement.getMovementDate());
        accountStatementResponse.setClient(client.getName());
        accountStatementResponse.setAccountNumber(account.getAccountNumber());
        accountStatementResponse.setAccountType(account.getAccountType());
        accountStatementResponse.setInitBalance(movement.getInitialBalance());
        accountStatementResponse.setStatus(account.getStatus());
        accountStatementResponse.setMovementValue(movement.getValue());
        accountStatementResponse.setAvailableBalance(movement.getBalance());
        return accountStatementResponse;
    }
}
