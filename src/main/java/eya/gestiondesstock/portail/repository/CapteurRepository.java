package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Capteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapteurRepository extends JpaRepository<Capteur , Long> {
}
