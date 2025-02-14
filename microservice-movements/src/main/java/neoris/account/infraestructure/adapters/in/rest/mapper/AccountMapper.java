package neoris.account.infraestructure.adapters.in.rest.mapper;

import neoris.account.domain.Account;
import neoris.account.infraestructure.adapters.in.rest.dto.AccountDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toAccountDTO(Account account);

    List<AccountDTO> toAccountDTOs(List<Account> accounts);

    Account toAccount(AccountDTO accountDTO);
}
