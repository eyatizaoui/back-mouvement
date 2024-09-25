package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article , Long> {
    List<Article> findByCategoryId(Long categoryId);
    Optional<Article> findByCodeBarre(String codeBarre);
    @Query(nativeQuery = true , value = "SELECT *\n" +
            "FROM article\n" +
            "ORDER BY quantity_total DESC\n" +
            "LIMIT 5")
    List<Article> topFive();


    @Query(value = "SELECT a.name , COUNT(e.id) FROM emplacement e " +
            "JOIN stock s ON e.stock_id = s.id " +
            "JOIN article a ON a.id = s.article_id " +
            "GROUP BY a.id " +
            "ORDER BY COUNT(e.id) ASC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Object[]> findTop5ArticlesByEmplacementCount();
}
