package neoris.infraestructure.adapters.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String identifier;
    private String name;
    private String address;
    private String phone;
    private String gender;
    private String birthday;
    private String password;
    private Boolean status;
}

