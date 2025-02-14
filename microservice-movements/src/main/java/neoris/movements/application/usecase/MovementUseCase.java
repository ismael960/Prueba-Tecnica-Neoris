package neoris.movements.application.usecase;

import neoris.account.application.ports.out.AccountOutPort;
import neoris.movements.application.ports.in.MovementInPort;
import neoris.movements.application.ports.out.MovementOutPort;
import neoris.movements.domain.Movement;
import neoris.movements.domain.MovementRequest;
import neoris.movements.domain.MovementResponse;
import neoris.account.infraestructure.exception.MovementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class MovementUseCase implements MovementInPort {

    private static final String DEPOSITO = "DEPOSITO";
    private static final String RETIRO = "RETIRO";

    private final MovementOutPort movementOutPort;
    private final AccountOutPort accountOutPort;

    @Autowired
    public MovementUseCase(MovementOutPort movementOutPort,
                           AccountOutPort accountOutPort) {
        this.movementOutPort = movementOutPort;
        this.accountOutPort = accountOutPort;
    }

    @Override
    public Mono<MovementResponse> save(MovementRequest movementData) {
        return accountOutPort.findByAccountNumber(movementData.getAccountNumber())
                .flatMap(account -> {
                    Movement movement = new Movement();
                    BigDecimal balance = new BigDecimal(account.getBalance());
                    if (movementData.getMovementType().equals(RETIRO)
                            && (balance.compareTo(BigDecimal.ZERO) <= 0 || balance.compareTo(movementData.getValue()) < 0))
                            return Mono.error(new MovementException("Saldo no disponible"));

                    movement.setInitialBalance(balance);

                    if (movementData.getMovementType().equals(RETIRO))
                        account.setBalance(balance.subtract(movementData.getValue()).toString());
                    else if (movementData.getMovementType().equals(DEPOSITO))
                        account.setBalance(balance.add(movementData.getValue()).toString());

                    accountOutPort.update(account, account.getId()).block();

                    movement.setMovementDate(LocalDateTime.now().toString());
                    movement.setMovementType(movementData.getMovementType() + " de " + movementData.getValue());
                    if (movementData.getMovementType().equals(RETIRO))
                        movement.setValue(movementData.getValue().negate());
                    else
                        movement.setValue(movementData.getValue());
                    movement.setBalance(new BigDecimal(account.getBalance()));
                    movement.setAccountId(account.getId());
                    movement = movementOutPort.save(movement).block();

                    MovementResponse movementResponse = new MovementResponse();
                    movementResponse.setMovementDate(LocalDateTime.parse(movement.getMovementDate()));
                    movementResponse.setMovementType(movement.getMovementType());
                    movementResponse.setValue(movement.getValue());
                    movementResponse.setBalance(movement.getBalance());
                    movementResponse.setAccountNumber(account.getAccountNumber());
                    return Mono.just(movementResponse);
                });
    }
}
