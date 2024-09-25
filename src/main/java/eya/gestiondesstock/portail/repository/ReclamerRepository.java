package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Reclamer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamerRepository extends JpaRepository<Reclamer , Long> {
    List<Reclamer> findByUtilisateurId(Long utilisateurId);
}
