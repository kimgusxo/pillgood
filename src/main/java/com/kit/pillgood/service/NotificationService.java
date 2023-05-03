package com.kit.pillgood.service;

import com.kit.pillgood.persistence.dto.NotificationDTO;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.NotificationRepository;
import com.kit.pillgood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class NotificationService {
//    private final NotificationRepository notificationRepository;
//    private final UserRepository userRepository;
//    private final GroupMemberRepository groupMemberRepository;
//    private final FirebaseMessaging firebaseMessaging;
//
//    @Autowired
//    public NotificationService(NotificationRepository notificationRepository,
//                               UserRepository userRepository,
//                               GroupMemberRepository groupMemberRepository,
//                               FirebaseMessaging firebaseMessaging) {
//        this.notificationRepository = notificationRepository;
//        this.userRepository = userRepository;
//        this.groupMemberRepository = groupMemberRepository;
//        this.firebaseMessaging  = firebaseMessaging;
//    }
//
//    /**
//     * 메소드의 간략한 설명
//     * @param: 파라미터 설명
//     * @return: 리턴 값 설명
//    **/
//    public void createAllNotification() {
//        // 모든 알림 생성
//    }
//
//    /**
//     * 메소드의 간략한 설명
//     * @param: 파라미터 설명
//     * @return: 리턴 값 설명
//    **/
//    public List<NotificationDTO> searchNotificationByUserIndex(Long userIndex) {
//        return notificationRepository.
//    }
//
//    /**
//     * 메소드의 간략한 설명
//     * @param: 파라미터 설명
//     * @return: 리턴 값 설명
//    **/
//    public NotificationDTO updateNotification(NotificationDTO notificationDTO) {
//        // 알림 수정
//    }
//
//    /**
//     * 메소드의 간략한 설명
//     * @param: 파라미터 설명
//     * @return: 리턴 값 설명
//    **/
//    public void sendAutoPushMessageNotification() {
//        // 자동 메세지 전송
//    }
//
//    /**
//     * 메소드의 간략한 설명
//     * @param: 파라미터 설명
//     * @return: 리턴 값 설명
//    **/
//    public void sendCreatedPrescriptionNotification(Long userIndex, Long groupMemberIndex) {
//        // 처방전 알림 생성 후 전송
//    }
//}
