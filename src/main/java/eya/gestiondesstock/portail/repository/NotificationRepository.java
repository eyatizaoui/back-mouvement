package eya.gestiondesstock.portail.repository;

import eya.gestiondesstock.portail.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification , Long> {
    boolean existsByArticleNameAndQuantity(String articleName, int quantity);
}
