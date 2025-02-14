package neoris.movements.infraestructure.adapters.in.rest.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import neoris.movements.application.ports.in.AccountStatementInPort;
import neoris.movements.application.ports.in.MovementInPort;
import neoris.movements.domain.MovementRequest;
import neoris.movements.infraestructure.adapters.in.rest.dto.AccountStatementDTO;
import neoris.movements.infraestructure.adapters.in.rest.dto.MovementRequestDTO;
import neoris.movements.infraestructure.adapters.in.rest.dto.MovementResponseDTO;
import neoris.movements.infraestructure.adapters.in.rest.mapper.MovementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = "/movimientos",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MovementController {

    private final MovementInPort movementInPort;
    private final MovementMapper movementMapper;
    private final AccountStatementInPort accountStatementInPort;

    @Autowired
    public MovementController(MovementInPort movementInPort,
                              MovementMapper movementMapper,
                              AccountStatementInPort accountStatementInPort) {
        this.movementInPort = movementInPort;
        this.movementMapper = movementMapper;
        this.accountStatementInPort = accountStatementInPort;
    }

    @PostMapping()
    public Mono<ResponseEntity<MovementResponseDTO>> saveMovement(@Valid @RequestBody MovementRequestDTO movementDTO) {
        log.info("saveMovement: {}", movementDTO);
        MovementRequest movementData = movementMapper.toMovementData(movementDTO);
        return movementInPort.save(movementData)
                .map(movementResponse -> ResponseEntity.ok(movementMapper.toMovementResponseDTO(movementResponse)));
    }

    @GetMapping("/reportes")
    public Mono<ResponseEntity<List<AccountStatementDTO>>> findMovements(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin,
            @RequestParam Long cliente
    ) {
        log.info("findMovements: fechaInicio={}, fechaFin={}, cliente={}", fechaInicio, fechaFin, cliente);
        return accountStatementInPort.getAccountStatement(fechaInicio, fechaFin, cliente)
                .map(accountStatementResponse -> ResponseEntity.ok(movementMapper.toAccountStatementDTO(accountStatementResponse)));
    }


}
