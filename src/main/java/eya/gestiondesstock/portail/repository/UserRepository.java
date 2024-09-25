package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Utilisateur,Long> {

    Optional<Utilisateur> findByUserName(String username);
    Optional<Utilisateur> findByEmail(String email);



}
