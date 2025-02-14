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
public class AccountStatementDTO {
    private String date;
    private String client;
    private String accountNumber;
    private String accountType;
    private BigDecimal initBalance;
    private Boolean status;
    private BigDecimal movementValue;
    private BigDecimal availableBalance;
}
