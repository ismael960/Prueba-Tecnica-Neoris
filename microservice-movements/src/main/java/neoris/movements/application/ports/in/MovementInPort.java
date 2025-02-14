package neoris.movements.application.ports.in;

import neoris.movements.domain.MovementRequest;
import neoris.movements.domain.MovementResponse;
import reactor.core.publisher.Mono;

public interface MovementInPort {

    Mono<MovementResponse> save(MovementRequest movement);

}
