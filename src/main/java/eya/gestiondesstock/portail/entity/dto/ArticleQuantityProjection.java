package eya.gestiondesstock.portail.entity.dto;

import java.time.LocalDate;

public interface ArticleQuantityProjection {
    Long getId();
   

    String getName();
    Long getQuantityEmpEnum0();
    Long getQuantityEmpEnum1();
    Long getQuantityEmpEnum2();
}