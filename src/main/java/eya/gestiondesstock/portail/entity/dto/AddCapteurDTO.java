package eya.gestiondesstock.portail.entity.dto;

import eya.gestiondesstock.portail.entity.Article;
import eya.gestiondesstock.portail.entity.TypeCapteur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCapteurDTO {

    private String name ;


    private long typeCapteur;


    private long article;
}
