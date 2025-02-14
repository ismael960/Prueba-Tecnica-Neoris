package neoris.infraestructure.adapters.out.sql;

import lombok.extern.slf4j.Slf4j;
import neoris.application.ports.out.ClientOutPort;
import neoris.domain.Client;
import neoris.infraestructure.adapters.out.sql.entity.ClientEntity;
import neoris.infraestructure.adapters.out.sql.mapper.ClientEntityMapper;
import neoris.infraestructure.adapters.out.sql.service.ClientService;
import neoris.infraestructure.exception.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ClientSqlAdapter implements ClientOutPort {

    private final ClientEntityMapper clientEntityMapper;
    private final ClientService clientService;

    @Autowired
    public ClientSqlAdapter(ClientEntityMapper clientEntityMapper,
                            ClientService clientService) {
        this.clientEntityMapper = clientEntityMapper;
        this.clientService = clientService;
    }

    @Override
    public Mono<Client> save(Client client) {
        return Mono.fromCompletionStage(() -> {
                    ClientEntity clientEntity = clientEntityMapper.toClientEntity(client);
                    clientEntity.setCreatedDate(LocalDateTime.now());
                    return clientService.save(clientEntity);
                })
                .doOnSuccess(clientEntity -> log.info("Client saved: " + clientEntity))
                .doOnError(throwable -> log.error("Error saving client: " + throwable))
                .map(clientEntityMapper::toClient);
    }

    @Override
    public Mono<Client> update(Client client, Long id) {
        return Mono.fromCompletionStage(() -> {
                    Optional<ClientEntity> clientEntity = clientService.findById(id).join();
                    if (clientEntity.isPresent()) {
                        clientEntity.get().setName(client.getName());
                        clientEntity.get().setAddress(client.getAddress());
                        clientEntity.get().setPhone(client.getPhone());
                        clientEntity.get().setGender(client.getGender());
                        clientEntity.get().setBirthday(LocalDate.parse(client.getBirthday()));
                        clientEntity.get().setPassword(client.getPassword());
                        clientEntity.get().setStatus(client.getStatus());
                        clientEntity.get().setModifiedDate(LocalDateTime.now());
                        return clientService.save(clientEntity.get());
                    }
                    throw new ClientException("Client not found");
                })
                .doOnSuccess(clientEntity -> log.info("Client updated: " + clientEntity))
                .doOnError(throwable -> log.error("Error updating client: " + throwable))
                .map(clientEntityMapper::toClient);
    }

    @Override
    public Mono<Boolean> delete(Long id) {
        return Mono.fromCompletionStage(clientService.delete(id))
                .doOnSuccess(aBoolean -> log.info("Client deleted: " + aBoolean))
                .doOnError(throwable -> log.error("Error deleting client: " + throwable));
    }

    @Override
    public Mono<List<Client>> findAll() {
        return Mono.fromCompletionStage(clientService.findAll())
                .doOnSuccess(clientEntities -> log.info("Clients found: " + clientEntities))
                .doOnError(throwable -> log.error("Error finding clients: " + throwable))
                .map(clientEntityMapper::toClients);
    }

    @Override
    public Mono<Client> getById(Long id) {
        return Mono.fromCompletionStage(() -> {
                    Optional<ClientEntity> clientEntity = clientService.findById(id).join();
                    if (clientEntity.isPresent()) {
                        return CompletableFuture.completedFuture(clientEntity.get());
                    }
                    throw new ClientException("Client not found");
                })
                .doOnSuccess(clientEntity -> log.info("Client found: " + clientEntity))
                .doOnError(throwable -> log.error("Error finding client: " + throwable))
                .map(clientEntityMapper::toClient);
    }
}
