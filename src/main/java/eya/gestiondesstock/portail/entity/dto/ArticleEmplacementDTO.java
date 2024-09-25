package eya.gestiondesstock.portail.entity.dto;

public class ArticleEmplacementDTO {
    private String name;
    private Long emplacementCount;

    public ArticleEmplacementDTO(String name, Long emplacementCount) {
        this.name = name;
        this.emplacementCount = emplacementCount;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEmplacementCount() {
        return emplacementCount;
    }

    public void setEmplacementCount(Long emplacementCount) {
        this.emplacementCount = emplacementCount;
    }
}
