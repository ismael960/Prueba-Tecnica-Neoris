package neoris.microservices.msclient.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {
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
