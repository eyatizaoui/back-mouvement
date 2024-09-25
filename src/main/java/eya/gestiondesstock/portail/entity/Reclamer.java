package eya.gestiondesstock.portail.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reclamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String content ;
    private String titre ;
    private LocalDateTime dateTime ;


    @ManyToOne
    private Utilisateur utilisateur ;

}
