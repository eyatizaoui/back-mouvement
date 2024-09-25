package eya.gestiondesstock.portail.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockHistoryPullDTO {
    private long stock;
    private int pull;
    private long pullBy;
}
