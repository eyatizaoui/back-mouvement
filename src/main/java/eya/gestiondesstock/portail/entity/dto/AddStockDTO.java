package eya.gestiondesstock.portail.entity.dto;


import eya.gestiondesstock.portail.entity.Article;
import eya.gestiondesstock.portail.entity.EmpEnum;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStockDTO {
    private int quantity ;
    private LocalDate dataFrom ;
    private LocalDate dateTo ;
    private String articleId;

}
