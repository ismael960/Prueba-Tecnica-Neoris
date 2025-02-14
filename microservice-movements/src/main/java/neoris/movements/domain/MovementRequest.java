package neoris.movements.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovementRequest {
    private String accountNumber;
    private String movementType;
    private BigDecimal value;
}
