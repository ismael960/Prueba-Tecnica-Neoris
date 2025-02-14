package neoris.infraestructure.adapters.out.sql.superclass;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class Person extends Audit {
    @Column(unique = true)
    private String identifier;
    private String name;
    private String address;
    private String phone;
    private String gender;
    private LocalDate birthday;
}
