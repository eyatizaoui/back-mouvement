package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByArticleIdOrderByDateToDesc(Long articleId);
}
