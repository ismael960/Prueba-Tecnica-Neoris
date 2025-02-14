package neoris.movements.infraestructure.adapters.out.sql.entity;

import jakarta.persistence.*;
import lombok.*;
import neoris.account.infraestructure.adapters.out.sql.superclass.Audit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "movement")
public class MovementEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Long id;

    @Column(name = "movement_date")
    private LocalDateTime movementDate;

    @Column(name = "movement_type")
    private String movementType;
    private BigDecimal value;

    @Column(name = "initial_balance")
    private BigDecimal initialBalance;

    private BigDecimal balance;

    @Column(name = "account_id")
    private Long accountId;


}
