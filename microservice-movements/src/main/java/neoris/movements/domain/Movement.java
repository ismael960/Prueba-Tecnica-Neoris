package neoris.movements.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movement {
    private Long id;
    private String movementDate;
    private String movementType;
    private BigDecimal value;
    private BigDecimal initialBalance;
    private BigDecimal balance;
    private Long accountId;
}
