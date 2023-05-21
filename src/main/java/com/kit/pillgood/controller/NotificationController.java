package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.NotificationDTO;
import com.kit.pillgood.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<ResponseFormat> getNotificationsByUserIndex(@PathVariable(name="user-index") Long userIndex) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("성공 코드", HttpStatus.OK.value(), notificationService.searchNotificationByUserIndex(userIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
