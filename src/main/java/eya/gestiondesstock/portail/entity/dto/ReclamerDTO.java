package eya.gestiondesstock.portail.entity.dto;


import eya.gestiondesstock.portail.entity.Utilisateur;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReclamerDTO {
    Long id;

    private String content ;
    private String titre ;




    private long utilisateur ;
}
