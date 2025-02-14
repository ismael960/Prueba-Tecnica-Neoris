package neoris.account.infraestructure.adapters.out.sql.entity;

import jakarta.persistence.*;
import lombok.*;
import neoris.account.infraestructure.adapters.out.sql.superclass.Audit;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "account",
        indexes = {
                @Index(columnList = "account_number,client_id", name = "account_number_client_id_idx")
        }
)
public class AccountEntity extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "account_type")
    private String accountType;
    private String balance;
    private Boolean status;

    @Column(name = "client_id")
    private Long clientId;
}
