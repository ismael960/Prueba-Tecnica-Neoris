package neoris.movements.infraestructure.adapters.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovementResponseDTO {
    private LocalDateTime movementDate;
    private String movementType;
    private BigDecimal value;
    private BigDecimal initialBalance;
    private BigDecimal balance;
    private String accountNumber;
}
