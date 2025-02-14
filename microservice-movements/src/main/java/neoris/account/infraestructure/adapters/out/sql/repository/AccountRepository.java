package neoris.account.infraestructure.adapters.out.sql.repository;

import neoris.account.infraestructure.adapters.out.sql.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    AccountEntity findByAccountNumber(String accountNumber);

    List<AccountEntity> findByClientId(Long clientId);
}
