package neoris.movements.infraestructure.adapters.out.sql.mapper;

import neoris.movements.domain.Movement;
import neoris.movements.infraestructure.adapters.out.sql.entity.MovementEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovementEntityMapper {
    Movement toMovement(MovementEntity movementEntity);

    List<Movement> toMovements(List<MovementEntity> movementEntities);

    MovementEntity toMovementEntity(Movement movement);
}
