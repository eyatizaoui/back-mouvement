package eya.gestiondesstock.portail.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private LocalDateTime localDateTime;

    @ManyToOne
    private Stock stock;

    private int pull ;

    private int put ;

    @ManyToOne
    @JsonManagedReference(value = "addedBy")
    private Utilisateur addedBy ;


    @ManyToOne
    @JsonManagedReference(value = "pullBy")
    private Utilisateur pullBy ;
}
