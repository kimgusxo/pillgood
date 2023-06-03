package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonRegistrationNotificationException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * 사용자의 알림 리스트 조회
     * @param: Long userIndex, 조회할 사용자 인덱스
     * @return: ResponseEntity<ResponseFormat>, 알림 리스트 결과가 담긴 응답 객체
     **/
    @GetMapping("/search/{user-index}")
    public ResponseEntity<ResponseFormat> getNotificationsByUserIndex(@PathVariable(name="user-index") Long userIndex) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), notificationService.searchNotificationByUserIndex(userIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 사용자의 알림 확인 여부 수정
     * @param: Long notificationIndex, 확인 여부를 수정할 알림 인덱스
     * @return: ResponseEntity<ResponseFormat>, 알림 확인 여부 결과가 담긴 응답 객체
     **/
    @PutMapping("/update/notification-check/{notification-index}")
    public ResponseEntity<ResponseFormat> updateNotificationCheckToTrue(@PathVariable(name="notification-index") Long notificationIndex) throws NonRegistrationNotificationException, NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), notificationService.updateNotificationCheck(notificationIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
