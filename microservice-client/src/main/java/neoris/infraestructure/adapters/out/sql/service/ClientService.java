package neoris.infraestructure.adapters.out.sql.service;

import neoris.infraestructure.adapters.out.sql.entity.ClientEntity;
import neoris.infraestructure.adapters.out.sql.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public CompletableFuture<ClientEntity> save(ClientEntity clientEntity) {
        return CompletableFuture.supplyAsync(() -> clientRepository.save(clientEntity));
    }

    public CompletableFuture<Optional<ClientEntity>> findById(Long id) {
        return CompletableFuture.supplyAsync(() -> clientRepository.findById(id));
    }

    public CompletableFuture<ClientEntity> findByIdentifier(String identifier) {
        return CompletableFuture.supplyAsync(() -> clientRepository.findByIdentifier(identifier));
    }

    public CompletableFuture<Boolean> delete(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            clientRepository.deleteById(id);
            return true;
        });
    }

    public CompletableFuture<List<ClientEntity>> findAll() {
        return CompletableFuture.supplyAsync(clientRepository::findAll);
    }
}
