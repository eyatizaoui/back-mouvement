package eya.gestiondesstock.portail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private int quantity ;

    private LocalDate dataFrom ;
    private LocalDate dateTo ;

    private LocalDateTime createdAt ;



    @ManyToOne
    private Article article;

    @JsonIgnore
    @OneToMany
    private List<Stock> stockList;


    @JsonIgnore
    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StockHistory> stockHistoryList = new ArrayList<>();
}
