package eya.gestiondesstock.portail.controller;


import eya.gestiondesstock.portail.entity.Capteur;
import eya.gestiondesstock.portail.entity.TypeCapteur;
import eya.gestiondesstock.portail.entity.dto.AddCapteurDTO;
import eya.gestiondesstock.portail.repository.CapteurRepository;
import eya.gestiondesstock.portail.repository.TypeCapteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/capteurs")
public class CapteurController {
    @Autowired
    private CapteurRepository capteurRepository;
    @Autowired
    private TypeCapteurRepository typeCapteurRepository ;

    @GetMapping
    public List<Capteur> getAllCapteurs() {
        return capteurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Capteur> getCapteurById(@PathVariable Long id) {
        return capteurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Capteur createCapteur(@RequestBody AddCapteurDTO addCapteurDTO) {
        TypeCapteur typeCapteur = typeCapteurRepository.findById(addCapteurDTO.getTypeCapteur()).orElse(null);

        Capteur capteur = new Capteur();
        capteur.setTypeCapteur(typeCapteur);
        capteur.setName(addCapteurDTO.getName());
        return capteurRepository.save(capteur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Capteur> updateCapteur(@PathVariable Long id, @RequestBody Capteur capteurDetails) {
        return capteurRepository.findById(id)
                .map(capteur -> {
                    capteur.setName(capteurDetails.getName());
                    capteur.setTypeCapteur(capteurDetails.getTypeCapteur());
                    return ResponseEntity.ok(capteurRepository.save(capteur));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCapteur(@PathVariable Long id) {
        return capteurRepository.findById(id)
                .map(capteur -> {
                    capteurRepository.delete(capteur);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
