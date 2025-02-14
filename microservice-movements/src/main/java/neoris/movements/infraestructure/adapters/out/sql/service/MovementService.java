package neoris.movements.infraestructure.adapters.out.sql.service;

import jakarta.transaction.Transactional;
import neoris.movements.infraestructure.adapters.out.sql.entity.MovementEntity;
import neoris.movements.infraestructure.adapters.out.sql.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class MovementService {

    private final MovementRepository movementRepository;

    @Autowired
    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public CompletableFuture<MovementEntity> save(MovementEntity movementEntity) {
        return CompletableFuture.supplyAsync(() -> movementRepository.saveAndFlush(movementEntity));
    }

    public CompletableFuture<List<MovementEntity>> getAccountStatement(LocalDateTime startDate, LocalDateTime endDate, Long accountId) {
        return CompletableFuture.supplyAsync(() -> movementRepository.getAccountStatement(startDate, endDate, accountId));
    }
}
