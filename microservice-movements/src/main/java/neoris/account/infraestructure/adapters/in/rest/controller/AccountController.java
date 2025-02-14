package neoris.account.infraestructure.adapters.in.rest.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import neoris.account.application.ports.in.AccountInPort;
import neoris.account.infraestructure.adapters.in.rest.dto.AccountDTO;
import neoris.account.infraestructure.adapters.in.rest.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(
        value = "/cuentas",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AccountController {

    private final AccountInPort accountInPort;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountController(AccountInPort accountInPort, AccountMapper accountMapper) {
        this.accountInPort = accountInPort;
        this.accountMapper = accountMapper;
    }

    @PostMapping()
    public Mono<ResponseEntity<AccountDTO>> saveAccount(@Valid @RequestBody AccountDTO accountDTO) {
        return accountInPort.save(accountMapper.toAccount(accountDTO))
                .map(accountMapper::toAccountDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<AccountDTO>> updateAccount(@Valid @RequestBody AccountDTO accountDTO,
                                                          @PathVariable Long id) {
        return accountInPort.update(accountMapper.toAccount(accountDTO), id)
                .map(accountMapper::toAccountDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Boolean>> deleteAccount(@PathVariable Long id) {
        return accountInPort.delete(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public Mono<ResponseEntity<List<AccountDTO>>> findAll() {
        return accountInPort.findAll()
                .map(accountMapper::toAccountDTOs)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/accountsClient/{id}")
    public Mono<ResponseEntity<List<AccountDTO>>> findAccountByClient(@PathVariable Long id) {
        return accountInPort.findAccountsByClient(id)
                .map(accountMapper::toAccountDTOs)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


}
