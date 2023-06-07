package com.kit.pillgood.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.NonRegistrationNotificationException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.repository.NotificationRepository;
import com.kit.pillgood.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    @Autowired
    public NotificationController(NotificationService notificationService, NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
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
    public ResponseEntity<ResponseFormat> updateNotificationCheckToTrue(@PathVariable(name="notification-index") Long notificationIndex) throws NonRegistrationNotificationException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), notificationService.updateNotificationCheck(notificationIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    @PostMapping("/test")
    public void test() throws EtcFirebaseException {
        try {
            String contents = "김주호님 아침약 알림 입니다.";
            String phone = "010-1111-1111";
            String token = "cR4WuUraS7mOe4BoLqbLkV:APA91bHIlJmxMOoE4VShfViSisIImqlC3L4OmPQ74nor9pnD7JSyUQL9FBSQubBr8GDeVr4qyAZg2ELQD_ebzG5GNJ78MqNdKIr3TNl9QBNFJ_RnrZft9Exg2sYGNSJczUmR_K9v5Ogd";
            Message messages = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("알림")
                            .setBody(contents)
                            .build())
                    .putData("유저 인덱스", "89")
                    .putData("그룹원 전화번호", phone)
                    .setToken(token)
                    .build();

            com.kit.pillgood.domain.Notification notification = com.kit.pillgood.domain.Notification.builder()
                    .notificationIndex(null)
                    .notificationTime(LocalDateTime.now())
                    .notificationContent(contents)
                    .user(User.builder().userIndex(89L).build())
                    .notificationCheck(false)
                    .build();

            String response;
            response = FirebaseMessaging.getInstance().send(messages);
            System.out.println(response);

            notification = notificationRepository.save(notification);
            System.out.println(notification);


        } catch (FirebaseMessagingException e) {
            throw new EtcFirebaseException();
        }
    }

}
