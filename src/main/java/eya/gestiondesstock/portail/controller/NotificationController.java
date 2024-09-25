package eya.gestiondesstock.portail.controller;

import eya.gestiondesstock.portail.entity.Notification;
import eya.gestiondesstock.portail.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping
    public List<Notification> getAll(){
        return notificationRepository.findAll();
    }

    @DeleteMapping
    public void delete(){
        notificationRepository.deleteAll();
    }
}
