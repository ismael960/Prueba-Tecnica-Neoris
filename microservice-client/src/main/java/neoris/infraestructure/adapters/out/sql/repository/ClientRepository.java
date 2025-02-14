package neoris.infraestructure.adapters.out.sql.repository;

import neoris.infraestructure.adapters.out.sql.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    ClientEntity findByIdentifier(String identifier);
}
