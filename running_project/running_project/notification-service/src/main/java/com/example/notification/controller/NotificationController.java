package com.example.notification.controller;
import com.example.notification.model.Notification;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/notify")
public class NotificationController {
    private final List<Notification> notifications = new ArrayList<>();

   
    @PostMapping
    public Notification sendNotification(@RequestBody Notification n) {
        Notification newNotification = new Notification();
        newNotification.setId(UUID.randomUUID().toString());
        newNotification.setType(n.getType());
        newNotification.setRecipient(n.getRecipient());
        newNotification.setMessage(n.getMessage());
        newNotification.setTimestamp(Instant.now().toString());
        notifications.add(newNotification);
        return newNotification;
    }

    
    @GetMapping
    public List<Notification> getAll() {
        return notifications;
    }
}