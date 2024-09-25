package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.Reclamer;
import eya.gestiondesstock.portail.entity.Utilisateur;
import eya.gestiondesstock.portail.entity.dto.ReclamerDTO;
import eya.gestiondesstock.portail.repository.ReclamerRepository;
import eya.gestiondesstock.portail.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/reclamations")
public class ReclamerController {

    @Autowired
    private ReclamerRepository reclamerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Reclamer> getAllReclamations() {
        return reclamerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reclamer getReclamationById(@PathVariable Long id) {
        Optional<Reclamer> optionalReclamer = reclamerRepository.findById(id);
        if (optionalReclamer.isPresent()) {
            return optionalReclamer.get();
        } else {
            throw new RuntimeException("Reclamation not found for id :: " + id);
        }
    }
    @GetMapping("/user/{userId}")
    public List<Reclamer> getReclamationsByUserId(@PathVariable Long userId) {
        return reclamerRepository.findByUtilisateurId(userId);
    }

    @PostMapping
    public Reclamer createReclamation(@RequestBody ReclamerDTO reclamerDTO) {
        Optional<Utilisateur> utilisateur = userRepository.findById(reclamerDTO.getUtilisateur());
        if(utilisateur.isPresent()){
            Reclamer reclamer = new Reclamer();
            reclamer.setContent(reclamerDTO.getContent());
            reclamer.setTitre(reclamerDTO.getTitre());
            reclamer.setDateTime(LocalDateTime.now());
            reclamer.setUtilisateur(utilisateur.get());
            return reclamerRepository.save(reclamer);
        }else {
            throw new RuntimeException("User not found for id :: " + reclamerDTO.getUtilisateur());
        }

    }

    @PutMapping("/{id}")
    public Reclamer updateReclamation(@PathVariable Long id, @RequestBody Reclamer reclamerDetails) {
        Reclamer reclamer = reclamerRepository.findById(id).orElseThrow(() -> new RuntimeException("Reclamation not found for id :: " + id));

        reclamer.setContent(reclamerDetails.getContent());
        reclamer.setTitre(reclamerDetails.getTitre());
        reclamer.setDateTime(reclamerDetails.getDateTime());
        reclamer.setUtilisateur(reclamerDetails.getUtilisateur());

        return reclamerRepository.save(reclamer);
    }

    @DeleteMapping("/{id}")
    public void deleteReclamation(@PathVariable Long id) {
        Reclamer reclamer = reclamerRepository.findById(id).orElseThrow(() -> new RuntimeException("Reclamation not found for id :: " + id));
        reclamerRepository.delete(reclamer);
    }
}