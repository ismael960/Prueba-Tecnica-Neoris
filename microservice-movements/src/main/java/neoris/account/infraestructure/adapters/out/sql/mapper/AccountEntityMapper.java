package neoris.account.infraestructure.adapters.out.sql.mapper;

import neoris.account.domain.Account;
import neoris.account.infraestructure.adapters.out.sql.entity.AccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountEntityMapper {
    Account toAccount(AccountEntity accountEntity);

    List<Account> toAccounts(List<AccountEntity> accountEntities);

    AccountEntity toAccountEntity(Account account);
}
