package neoris.movements.infraestructure.adapters.in.rest.mapper;

import neoris.movements.domain.AccountStatementResponse;
import neoris.movements.domain.MovementRequest;
import neoris.movements.domain.MovementResponse;
import neoris.movements.infraestructure.adapters.in.rest.dto.AccountStatementDTO;
import neoris.movements.infraestructure.adapters.in.rest.dto.MovementRequestDTO;
import neoris.movements.infraestructure.adapters.in.rest.dto.MovementResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovementMapper {
    MovementRequest toMovementData(MovementRequestDTO movementDTO);

    MovementResponseDTO toMovementResponseDTO(MovementResponse movementResponse);

    List<AccountStatementDTO> toAccountStatementDTO(List<AccountStatementResponse> accountStatementResponse);
}

