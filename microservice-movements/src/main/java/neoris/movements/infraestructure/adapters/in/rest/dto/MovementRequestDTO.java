package neoris.movements.infraestructure.adapters.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovementRequestDTO {
    private String accountNumber;
    private String movementType;
    private BigDecimal value;
}

