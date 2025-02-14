package neoris.microservices.msclient;

import neoris.microservices.msclient.domain.Client;
import reactor.core.publisher.Mono;

public interface ClientConsumerOutPort {

    Mono<Client> getById(Long id);
}
