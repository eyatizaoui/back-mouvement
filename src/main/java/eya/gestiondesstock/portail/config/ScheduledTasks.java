package eya.gestiondesstock.portail.config;

import eya.gestiondesstock.portail.entity.EmpEnum;
import eya.gestiondesstock.portail.entity.Emplacement;
import eya.gestiondesstock.portail.entity.Notification;
import eya.gestiondesstock.portail.entity.StatusNotification;
import eya.gestiondesstock.portail.repository.EmplacementRepository;
import eya.gestiondesstock.portail.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledTasks {
    @Autowired
    private EmplacementRepository emplacementRepository;

    @Autowired
    private NotificationRepository notificationRepository;
    @Scheduled(fixedRate = 60000)
    public void performTask() {
        System.out.println("Task executed at fixed rate: " + System.currentTimeMillis());
        List<Emplacement> emplacements = emplacementRepository.findLowQuantityEmplacements(20 , EmpEnum.PA);
        System.out.println(emplacements.get(0).getQuantity());

        for (Emplacement emplacement : emplacements){
            if(!notificationRepository.existsByArticleNameAndQuantity(emplacement.getStock().getArticle().getName() , emplacement.getQuantity())){
                Notification notification = new Notification();
                notification.setArticleName(emplacement.getStock().getArticle().getName());
                notification.setStatusNotification(StatusNotification.NON_LU);
                notification.setQuantity(emplacement.getQuantity());
                notification.setLocalDateTime(LocalDateTime.now());
                notificationRepository.save(notification);
                System.out.println("notification saved !");
            }
            else{
                System.out.println("non notifcation");
            }


        }

    }
}