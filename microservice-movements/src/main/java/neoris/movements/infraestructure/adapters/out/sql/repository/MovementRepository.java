package neoris.movements.infraestructure.adapters.out.sql.repository;

import neoris.movements.infraestructure.adapters.out.sql.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<MovementEntity, Long> {

    @Query("SELECT me FROM MovementEntity me WHERE me.movementDate BETWEEN :startDate AND :endDate AND me.accountId = :accountId")
    List<MovementEntity> getAccountStatement(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Long accountId);

}
