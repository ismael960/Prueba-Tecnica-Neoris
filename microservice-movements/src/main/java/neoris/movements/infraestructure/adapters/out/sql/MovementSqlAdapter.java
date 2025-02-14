package neoris.movements.infraestructure.adapters.out.sql;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import neoris.movements.application.ports.out.MovementOutPort;
import neoris.movements.domain.Movement;
import neoris.movements.infraestructure.adapters.out.sql.entity.MovementEntity;
import neoris.movements.infraestructure.adapters.out.sql.mapper.MovementEntityMapper;
import neoris.movements.infraestructure.adapters.out.sql.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MovementSqlAdapter implements MovementOutPort {

    private final MovementEntityMapper movementEntityMapper;
    private final MovementService movementService;

    @Autowired
    public MovementSqlAdapter(MovementEntityMapper movementEntityMapper,
                              MovementService movementService) {
        this.movementEntityMapper = movementEntityMapper;
        this.movementService = movementService;
    }

    @Transactional
    @Override
    public Mono<Movement> save(Movement movement) {
        return Mono.fromCompletionStage(() -> {
                    MovementEntity movementEntity = movementEntityMapper.toMovementEntity(movement);
                    movementEntity.setCreatedDate(LocalDateTime.now());
                    return movementService.save(movementEntity);
                })
                .doOnSuccess(clientEntity -> log.info("Movement saved: " + clientEntity))
                .doOnError(throwable -> log.error("Error saving movement: " + throwable))
                .map(movementEntityMapper::toMovement);
    }

    @Override
    public Mono<List<Movement>> getAccountStatement(String startDate, String endDate, Long accountId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Convert String to LocalDate
        LocalDate sdFormated = LocalDate.parse(startDate, formatter);
        LocalDate edFormated = LocalDate.parse(endDate, formatter);

        // Create LocalDateTime from LocalDate
        LocalDateTime startDateTime = sdFormated.atStartOfDay();
        LocalDateTime endDateTime = edFormated.atTime(23, 59, 59);

        return Mono.fromCompletionStage(() -> movementService.getAccountStatement(startDateTime, endDateTime, accountId))
                .doOnSuccess(movementEntities -> log.info("Account statement retrieved: " + movementEntities))
                .doOnError(throwable -> log.error("Error retrieving account statement: " + throwable))
                .map(movementEntityMapper::toMovements);
    }
}
