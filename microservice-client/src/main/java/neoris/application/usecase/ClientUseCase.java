package neoris.application.usecase;

import neoris.application.ports.in.ClientInPort;
import neoris.application.ports.out.ClientOutPort;
import neoris.domain.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ClientUseCase implements ClientInPort {

    private final ClientOutPort clientOutPort;

    @Autowired
    public ClientUseCase(ClientOutPort clientOutPort) {
        this.clientOutPort = clientOutPort;
    }

    @Override
    public Mono<Client> save(Client client) {
        return clientOutPort.save(client);
    }

    @Override
    public Mono<Client> update(Client client, Long id) {
        return clientOutPort.update(client, id);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return clientOutPort.delete(id);
    }

    @Override
    public Mono<List<Client>> findAll() {
        return clientOutPort.findAll();
    }

    @Override
    public Mono<Client> getById(Long id) {
        return clientOutPort.getById(id);
    }
}
