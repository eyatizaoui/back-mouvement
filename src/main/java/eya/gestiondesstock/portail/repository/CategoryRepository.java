package eya.gestiondesstock.portail.repository;


import eya.gestiondesstock.portail.entity.Category;
import eya.gestiondesstock.portail.entity.dto.CategoryArticleCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category , Long> {
    Optional<Category> findByName(String name);


    @Query("SELECT c.id as categoryId, c.name as categoryName, COUNT(a.id) as articleCount " +
            "FROM Category c LEFT JOIN c.articleList a " +
            "GROUP BY c.id, c.name")
    List<CategoryArticleCount> findCategoryArticleCounts();
}
