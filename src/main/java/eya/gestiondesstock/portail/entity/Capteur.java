package eya.gestiondesstock.portail.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Capteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name ;

    @ManyToOne
    private TypeCapteur typeCapteur;

    @OneToOne
    private Article article;
}
