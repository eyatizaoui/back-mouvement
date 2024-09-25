package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.EmpEnum;
import eya.gestiondesstock.portail.entity.Emplacement;
import eya.gestiondesstock.portail.entity.dto.ArticleQuantityProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface EmplacementRepository extends JpaRepository<Emplacement, Long> {
    List<Emplacement> findByStockIdAndEmpEnum(Long stockId, EmpEnum empEnum);


    @Query("SELECT e.empEnum, e.localDateTime, SUM(e.quantity), e.stock " +
            "FROM Emplacement e " +
            "WHERE e.empEnum = :empEnum " +
            "GROUP BY e.stock.article.id")
    List<Object[]> findEmplacementByEmpEnum(EmpEnum empEnum);

    @Query("SELECT e.empEnum, e.localDateTime, SUM(e.quantity), e.stock " +
            "FROM Emplacement e " +
            "WHERE e.empEnum = :empEnum " +
            "GROUP BY e.stock.article.id " +
            "ORDER BY SUM(e.quantity) DESC")
    List<Object[]> findEmplacementByEmpEnumTop5(EmpEnum empEnum);


    @Query("SELECT e FROM Emplacement e WHERE e.quantity < :qts AND e.empEnum = :empEnum")
    List<Emplacement> findLowQuantityEmplacements(int qts, EmpEnum empEnum);


    @Query("SELECT s.id AS id , a.name AS name, " +
            "SUM(CASE WHEN e.empEnum = 0 THEN e.quantity ELSE 0 END) AS quantityEmpEnum0, " +
            "SUM(CASE WHEN e.empEnum = 1 THEN e.quantity ELSE 0 END) AS quantityEmpEnum1, " +
            "SUM(CASE WHEN e.empEnum = 2 THEN e.quantity ELSE 0 END) AS quantityEmpEnum2 " +
            "FROM Emplacement e " +
            "JOIN e.stock s " +
            "JOIN s.article a " +
            "GROUP BY a.id, a.name")
    List<ArticleQuantityProjection> findArticleQuantities();
}
