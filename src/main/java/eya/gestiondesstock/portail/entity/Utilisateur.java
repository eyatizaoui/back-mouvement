package eya.gestiondesstock.portail.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Utilisateur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userName;
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String password;

    LocalDate creationDate;
    boolean isActive;

    private Role role ;


    @JsonIgnore
    @OneToMany
    private List<Image> imageList;
    @OneToMany(mappedBy = "addedBy")
    @JsonIgnore
    private List<StockHistory> addedStockHistories;

    @OneToMany(mappedBy = "pullBy")
    @JsonIgnore
    private List<StockHistory> pulledStockHistories;

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reclamer> ReclamerList = new ArrayList<>();

}
