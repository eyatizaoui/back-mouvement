package eya.gestiondesstock.portail.entity.dto;



import eya.gestiondesstock.portail.entity.EmpEnum;
import eya.gestiondesstock.portail.entity.Stock;
import lombok.Getter;

import java.time.LocalDateTime;

public class EmplacementDTO {

    private EmpEnum empEnum;
    @Getter
    private LocalDateTime localDateTime;
    private int quantity;
    private Stock stock;

    // Constructors
    public EmplacementDTO(EmpEnum empEnum, LocalDateTime localDateTime, int quantity, Stock stock) {
        this.empEnum = empEnum;
        this.localDateTime = localDateTime;
        this.quantity = quantity;
        this.stock = stock;
    }

    // Getters and Setters
    public EmpEnum getEmpEnum() {
        return empEnum;
    }

    public void setEmpEnum(EmpEnum empEnum) {
        this.empEnum = empEnum;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Stock getStockId() {
        return stock;
    }

    public void setStockId(Stock stock) {
        this.stock = stock;
    }
}
