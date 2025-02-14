package neoris.movements.application.ports.in;

import neoris.movements.domain.AccountStatementResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountStatementInPort {

    Mono<List<AccountStatementResponse>> getAccountStatement(String startDate, String endDate, Long clientId);
}
