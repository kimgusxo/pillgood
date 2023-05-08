package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.NotificationDTO;
import com.kit.pillgood.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/search/{user-index}")
    public List<NotificationDTO> getNotificationsByUserIndex(@PathVariable(name="user-index") Long userIndex) {
        return notificationService.searchNotificationByUserIndex(userIndex);
    }

    @PutMapping("/search/{notification-index}")
    public NotificationDTO updateNotificationsByNotificationIndex(@Valid @PathVariable(name="notification-index") Long notificationIndex,
                                                                  @ModelAttribute NotificationDTO notificationDTO) {
        return notificationService.updateNotification(notificationIndex, notificationDTO);
    }
}
