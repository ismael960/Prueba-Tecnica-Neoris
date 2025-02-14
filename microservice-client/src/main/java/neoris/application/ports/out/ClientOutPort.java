package neoris.application.ports.out;

import neoris.domain.Client;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ClientOutPort {

    Mono<Client> save(Client client);

    Mono<Client> update(Client client, Long id);

    Mono<Boolean> delete(Long id);

    Mono<List<Client>> findAll();

    Mono<Client> getById(Long id);
}
