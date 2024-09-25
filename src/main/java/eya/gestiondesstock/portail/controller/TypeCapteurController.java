package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.TypeCapteur;
import eya.gestiondesstock.portail.repository.TypeCapteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/typeCapteurs")
public class TypeCapteurController {

    @Autowired
    private TypeCapteurRepository typeCapteurRepository;

    @GetMapping
    public List<TypeCapteur> getAllTypeCapteurs() {
        return typeCapteurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeCapteur> getTypeCapteurById(@PathVariable Long id) {
        Optional<TypeCapteur> typeCapteur = typeCapteurRepository.findById(id);
        return typeCapteur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TypeCapteur> createTypeCapteur(@RequestBody TypeCapteur typeCapteur) {
        TypeCapteur savedTypeCapteur = typeCapteurRepository.save(typeCapteur);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTypeCapteur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeCapteur> updateTypeCapteur(@PathVariable Long id, @RequestBody TypeCapteur updatedTypeCapteur) {
        Optional<TypeCapteur> optionalTypeCapteur = typeCapteurRepository.findById(id);
        if (optionalTypeCapteur.isPresent()) {
            TypeCapteur typeCapteur = optionalTypeCapteur.get();
            typeCapteur.setReference(updatedTypeCapteur.getReference());
            typeCapteur.setModele(updatedTypeCapteur.getModele());
            TypeCapteur savedTypeCapteur = typeCapteurRepository.save(typeCapteur);
            return ResponseEntity.ok(savedTypeCapteur);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeCapteur(@PathVariable Long id) {
        if (typeCapteurRepository.existsById(id)) {
            typeCapteurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
