package neoris.account.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Account {
    private Long id;
    private String accountNumber;
    private String accountType;
    private String balance;
    private Boolean status;
    private Long clientId;
    private String name;
}
