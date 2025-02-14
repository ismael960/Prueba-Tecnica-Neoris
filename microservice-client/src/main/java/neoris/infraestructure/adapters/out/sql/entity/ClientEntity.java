package neoris.infraestructure.adapters.out.sql.entity;

import jakarta.persistence.*;
import lombok.*;
import neoris.infraestructure.adapters.out.sql.superclass.Person;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "client")
public class ClientEntity extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientId")
    private Long id;
    private String password;
    private Boolean status;
}
