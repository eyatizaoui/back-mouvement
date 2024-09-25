package eya.gestiondesstock.portail.entity.dto;

import eya.gestiondesstock.portail.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String token ;
    private long id ;
    private Role role ;
}
