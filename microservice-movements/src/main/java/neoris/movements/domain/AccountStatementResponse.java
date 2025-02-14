package neoris.movements.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountStatementResponse {
    private String date;
    private String client;
    private String accountNumber;
    private String accountType;
    private BigDecimal initBalance;
    private Boolean status;
    private BigDecimal movementValue;
    private BigDecimal availableBalance;
}
