package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.EmpEnum;
import eya.gestiondesstock.portail.entity.Emplacement;
import eya.gestiondesstock.portail.entity.Stock;
import eya.gestiondesstock.portail.entity.dto.ArticleQuantityProjection;
import eya.gestiondesstock.portail.entity.dto.EmplacementDTO;
import eya.gestiondesstock.portail.repository.EmplacementRepository;
import eya.gestiondesstock.portail.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/emplacements")
public class EmplacementController {

    @Autowired
    private EmplacementRepository emplacementRepository;


    @Autowired
    private StockRepository stockRepository;
    @GetMapping("/top2/{stockId}/{nb}")
    public Emplacement top2(@PathVariable Long stockId , @PathVariable int nb) {
        Emplacement emplacement = emplacementRepository.findByStockIdAndEmpEnum(stockId, EmpEnum.PA).get(0);
        emplacement.setQuantity(emplacement.getQuantity() - nb);
        emplacementRepository.save(emplacement);
        Emplacement emplacement1 = new Emplacement();
        emplacement1.setQuantity(nb);
        emplacement1.setStock(emplacement.getStock());
        emplacement1.setEmpEnum(EmpEnum.KA);
        emplacement1.setLocalDateTime(LocalDateTime.now());

        return emplacementRepository.save(emplacement1);
    }

    @GetMapping
    public List<Emplacement> getAllEmplacements() {
        return emplacementRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Emplacement> getEmplacementById(@PathVariable Long id) {
        return emplacementRepository.findById(id);
    }

    @PostMapping
    public Emplacement createEmplacement(@RequestBody Emplacement emplacement) {
        return emplacementRepository.save(emplacement);
    }

    @GetMapping("/stock/{stockId}")
    public List<Emplacement> getEmplacementsByStockIdAndEmpEnum(@PathVariable Long stockId) {
        return emplacementRepository.findByStockIdAndEmpEnum(stockId, EmpEnum.PA);
    }

    @PutMapping("/{id}")
    public Emplacement updateEmplacement(@PathVariable Long id, @RequestBody Emplacement updatedEmplacement) {
        return emplacementRepository.findById(id).map(emplacement -> {
            emplacement.setEmpEnum(updatedEmplacement.getEmpEnum());
            emplacement.setQuantity(updatedEmplacement.getQuantity());
            emplacement.setLocalDateTime(updatedEmplacement.getLocalDateTime());
            emplacement.setStock(updatedEmplacement.getStock());
            return emplacementRepository.save(emplacement);
        }).orElseGet(() -> {
            updatedEmplacement.setId(id);
            return emplacementRepository.save(updatedEmplacement);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteEmplacement(@PathVariable Long id) {
        emplacementRepository.deleteById(id);
    }


    @GetMapping("/emplacement/empEnum")
    public List<EmplacementDTO> getEmplacement(@RequestParam EmpEnum empEnum) {
        List<Object[]> results = emplacementRepository.findEmplacementByEmpEnum(empEnum);

        return results.stream().map(row -> new EmplacementDTO(
                (EmpEnum) row[0], // empEnum
                (LocalDateTime) row[1], // localDateTime
                ((Number) row[2]).intValue(), // quantity
                ((Stock) row[3]) // stockId
        )).collect(Collectors.toList());
    }

    @GetMapping("/emplacements/low-quantity")
    public List<Emplacement> getLowQuantityEmplacements() {
        return emplacementRepository.findLowQuantityEmplacements(20 , EmpEnum.PA);
    }


    @GetMapping("/emplacements/top5")
    public List<EmplacementDTO> getTop5() {
        List<Object[]> results = emplacementRepository.findEmplacementByEmpEnumTop5(EmpEnum.PA);

        return results.stream().map(row -> new EmplacementDTO(
                (EmpEnum) row[0], // empEnum
                (LocalDateTime) row[1], // localDateTime
                ((Number) row[2]).intValue(), // quantity
                ((Stock) row[3]) // stockId
        )).collect(Collectors.toList());
    }

    @GetMapping("/emplacements/article-quantities")
    public List<ArticleQuantityProjection> getArticleQuantities() {
        return emplacementRepository.findArticleQuantities();
    }
}
