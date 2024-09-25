package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.*;
import eya.gestiondesstock.portail.entity.dto.StockHistoryPullDTO;
import eya.gestiondesstock.portail.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/stock-history")
public class StockHistoryController {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockHistoryRepository stockHistoryRepository;
    @Autowired
    private EmplacementRepository emplacementRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @GetMapping
    public List<StockHistory> getAllStockHistories() {
        return stockHistoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockHistory> getStockHistoryById(@PathVariable Long id) {
        Optional<StockHistory> stockHistory = stockHistoryRepository.findById(id);
        return stockHistory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StockHistory createStockHistory(@RequestBody StockHistory stockHistory) {
        return stockHistoryRepository.save(stockHistory);
    }

    @GetMapping("push/{stockHisID}/{nb}")
    public ResponseEntity<?> pushStock(@PathVariable Long stockHisID ,@PathVariable int nb ){
        StockHistory stockHistory = stockHistoryRepository.findById(stockHisID).orElse(null);
        stockHistory.setPut(nb);
        stockHistory.setAddedBy(stockHistory.getPullBy());
        stockHistoryRepository.save(stockHistory);

        List<Emplacement> emplacement = emplacementRepository.findByStockIdAndEmpEnum(stockHistory.getStock().getId() , EmpEnum.SE);
        Emplacement emplacement1 = emplacement.get(0);
        emplacement1.setQuantity(emplacement1.getQuantity() - nb);
        emplacementRepository.save(emplacement1);
        //VqtD884WcsLd
        //iLkD29VkekFK
        List<Emplacement> emplacement3 = emplacementRepository.findByStockIdAndEmpEnum(stockHistory.getStock().getId() , EmpEnum.SE);
        Emplacement emplacement2 = emplacement3.get(0);
        emplacement2.setQuantity(emplacement2.getQuantity() + nb);
        emplacementRepository.save(emplacement2);
        return ResponseEntity.ok(stockHistory);

    }

    @PostMapping("pull")
    public ResponseEntity<?> pullStock(@RequestBody List<StockHistoryPullDTO> stockHistoryPullDTOList) {
        List<StockHistory> stockHistories = new ArrayList<>();
        System.out.println(stockHistoryPullDTOList);
        for (StockHistoryPullDTO stockHistoryPullDTO : stockHistoryPullDTOList){
            Optional<Utilisateur> utilisateur = userRepository.findById(stockHistoryPullDTO.getPullBy());
            Optional<Stock> stock = stockRepository.findById(stockHistoryPullDTO.getStock());
            List<Emplacement> emplacement = emplacementRepository.findByStockIdAndEmpEnum(stock.get().getId() , EmpEnum.KA);
            if(utilisateur.isPresent() && stock.isPresent()){
                StockHistory stockHistory = new StockHistory();
                stockHistory.setStock(stock.get());
                stockHistory.setPull(stockHistoryPullDTO.getPull());
                stockHistory.setPullBy(utilisateur.get());
                stockHistory.setLocalDateTime(LocalDateTime.now());
                stockHistories.add(stockHistory);

                Emplacement emplacementToUpdate = emplacement.get(0);
                emplacementToUpdate.setQuantity(emplacementToUpdate.getQuantity() - stockHistoryPullDTO.getPull());
                emplacementRepository.save(emplacementToUpdate);



                Emplacement emplacement1 = new Emplacement();
                emplacement1.setQuantity(stockHistory.getPull());
                emplacement1.setEmpEnum(EmpEnum.SE);
                emplacement1.setStock(stock.get());
                emplacement1.setLocalDateTime(LocalDateTime.now());
                emplacementRepository.save(emplacement1);
            }
        }

        return ResponseEntity.ok(stockHistoryRepository.saveAll(stockHistories));

    }

    @PutMapping("/{id}")
    public ResponseEntity<StockHistory> updateStockHistory(@PathVariable Long id, @RequestBody StockHistory stockHistoryDetails) {
        Optional<StockHistory> stockHistoryOptional = stockHistoryRepository.findById(id);

        if (stockHistoryOptional.isPresent()) {
            StockHistory stockHistory = stockHistoryOptional.get();
            stockHistory.setLocalDateTime(stockHistoryDetails.getLocalDateTime());
            stockHistory.setStock(stockHistoryDetails.getStock());
            stockHistory.setPull(stockHistoryDetails.getPull());
            stockHistory.setPut(stockHistoryDetails.getPut());
            stockHistory.setAddedBy(stockHistoryDetails.getAddedBy());
            stockHistory.setPullBy(stockHistoryDetails.getPullBy());

            StockHistory updatedStockHistory = stockHistoryRepository.save(stockHistory);
            return ResponseEntity.ok(updatedStockHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStockHistory(@PathVariable Long id) {
        if (stockHistoryRepository.findById(id).isPresent()) {
            stockHistoryRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}