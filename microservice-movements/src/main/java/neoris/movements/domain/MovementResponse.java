package neoris.movements.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovementResponse {
    private LocalDateTime movementDate;
    private String movementType;
    private BigDecimal value;
    private BigDecimal initialBalance;
    private BigDecimal balance;
    private String accountNumber;
}
