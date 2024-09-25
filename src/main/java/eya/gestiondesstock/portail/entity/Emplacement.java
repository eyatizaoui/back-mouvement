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
public class Emplacement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private EmpEnum empEnum ;

    private int quantity ;

    private LocalDateTime localDateTime ;

    @ManyToOne
    private Stock stock ;
}
