package neoris.movements.application.ports.out;

import neoris.movements.domain.Movement;
import reactor.core.publisher.Mono;

import java.util.List;


public interface MovementOutPort {

    Mono<Movement> save(Movement movement);

    Mono<List<Movement>> getAccountStatement(String startDate, String endDate, Long accountId);
}
