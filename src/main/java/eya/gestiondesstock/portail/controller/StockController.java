package eya.gestiondesstock.portail.controller;


import eya.gestiondesstock.portail.entity.Article;
import eya.gestiondesstock.portail.entity.EmpEnum;
import eya.gestiondesstock.portail.entity.Emplacement;
import eya.gestiondesstock.portail.entity.Stock;
import eya.gestiondesstock.portail.entity.dto.AddStockDTO;
import eya.gestiondesstock.portail.repository.ArticleRepository;
import eya.gestiondesstock.portail.repository.EmplacementRepository;
import eya.gestiondesstock.portail.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EmplacementRepository emplacementRepository;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Optional<Stock> stock = stockRepository.findById(id);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @Transactional
    @PostMapping
    public ResponseEntity<?> createStock(@RequestBody AddStockDTO addStockDTO) {
        Article article = articleRepository.findById(Long.valueOf(addStockDTO.getArticleId())).orElse(null);
        if(article == null){
            return ResponseEntity.badRequest().body("Article not found");
        }
        Stock stock = new Stock();
        stock.setQuantity(addStockDTO.getQuantity());

        stock.setDateTo(addStockDTO.getDateTo());
        stock.setDataFrom(addStockDTO.getDataFrom());
        stock.setArticle(article);
        stock.setCreatedAt(LocalDateTime.now());
        article.setQuantityTotal(article.getQuantityTotal() + addStockDTO.getQuantity());
        articleRepository.save(article);
        Stock stockSave = stockRepository.save(stock);
        Emplacement emplacement = new Emplacement();
        emplacement.setLocalDateTime(LocalDateTime.now());
        emplacement.setQuantity(addStockDTO.getQuantity());
        emplacement.setEmpEnum(EmpEnum.PA);
        emplacement.setStock(stockSave);
        emplacementRepository.save(emplacement);

        return ResponseEntity.ok(stockSave);
    }

    @PutMapping("/{id}/{idArticle}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @PathVariable Long idArticle,@RequestBody Stock stockDetails) {
        Optional<Stock> optionalStock = stockRepository.findById(id);
        Optional<Article> optionalArtcile = articleRepository.findById(idArticle);
        System.out.println(optionalStock.isPresent() && optionalArtcile.isPresent());
        if (optionalStock.isPresent() && optionalArtcile.isPresent()) {
            Stock stock = optionalStock.get();
            stock.setQuantity(stockDetails.getQuantity());
            stock.setDataFrom(stockDetails.getDataFrom());
            stock.setDateTo(stockDetails.getDateTo());

            stock.setArticle(optionalArtcile.get());


            Stock updatedStock = stockRepository.save(stock);
            return ResponseEntity.ok(updatedStock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        if (stockRepository.existsById(id)) {
            stockRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-article/{articleId}")
    public List<Stock> getStocksByArticleId(@PathVariable Long articleId) {
        return stockRepository.findByArticleIdOrderByDateToDesc(articleId);
    }
}