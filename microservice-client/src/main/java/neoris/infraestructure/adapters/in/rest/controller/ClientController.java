package neoris.infraestructure.adapters.in.rest.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import neoris.application.ports.in.ClientInPort;
import neoris.infraestructure.adapters.in.rest.dto.ClientDTO;
import neoris.infraestructure.adapters.in.rest.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = "/clientes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClientController {

    private final ClientInPort clientInPort;
    private final ClientMapper clientMapper;

    @Autowired
    public ClientController(ClientInPort clientInPort, ClientMapper clientMapper) {
        this.clientInPort = clientInPort;
        this.clientMapper = clientMapper;
    }

    @PostMapping()
    public Mono<ResponseEntity<ClientDTO>> saveClient(@Valid @RequestBody ClientDTO clientDTO) {
        return clientInPort.save(clientMapper.toClient(clientDTO))
                .map(clientMapper::toClientDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClientDTO>> updateClient(@Valid @RequestBody ClientDTO clientDTO,
                                                        @PathVariable Long id) {
        return clientInPort.update(clientMapper.toClient(clientDTO), id)
                .map(clientMapper::toClientDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Boolean>> deleteClient(@PathVariable Long id) {
        return clientInPort.delete(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public Mono<ResponseEntity<List<ClientDTO>>> findAll() {
        return clientInPort.findAll()
                .map(clientMapper::toClientDTOs)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ClientDTO>> getById(@PathVariable Long id) {
        return clientInPort.getById(id)
                .map(clientMapper::toClientDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
