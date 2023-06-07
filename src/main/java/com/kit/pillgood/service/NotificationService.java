package com.kit.pillgood.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.domain.Notification;
import com.kit.pillgood.exeptions.exeption.NonRegistrationNotificationException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.TransactionFailedException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.persistence.dto.NotificationContentDTO;
import com.kit.pillgood.persistence.dto.NotificationDTO;
import com.kit.pillgood.persistence.dto.PillScheduleDTO;
import com.kit.pillgood.persistence.projection.NotificationContentSummary;
import com.kit.pillgood.repository.NotificationRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Service
public class NotificationService {
    private com.google.firebase.messaging.Notification FirebaseNotification;
    private final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private List<NotificationContentDTO> wakeUpTimeNotifications = new ArrayList<>();
    private List<NotificationContentDTO> morningTimeNotifications = new ArrayList<>();
    private List<NotificationContentDTO> lunchTimeNotification = new ArrayList<>();
    private List<NotificationContentDTO> dinnerTimeNotifications = new ArrayList<>();
    private List<NotificationContentDTO> bedTimeNotifications = new ArrayList<>();

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<NotificationDTO> searchNotificationByUserIndex(Long userIndex) throws NonRegistrationUserException {
        try{
            if(!userRepository.existsByUserIndex(userIndex)){
                LOGGER.info(".searchNotificationByUserIndex [err] 존재하지 않은 userIndex={} 조회", userIndex);
                throw new NonRegistrationUserException();
            }

            LocalDate nowTime = LocalDate.now();
            LocalDateTime threeDayAgo = nowTime.minusDays(3).atStartOfDay();
            List<Notification> notifications = notificationRepository.findNotificationByUser_UserIndexAndNotificationTimeAfterAndNotificationCheckFalseOrderByNotificationTimeDesc(userIndex, threeDayAgo);

            List<NotificationDTO> notificationDTOs = new ArrayList<>();


            for(Notification notification : notifications) {
                NotificationDTO notificationDTO = EntityConverter.toNotificationDTO(notification);
                notificationDTOs.add(notificationDTO);
            }

            LOGGER.info(".searchNotificationByUserIndex 유저{} 알림 조회 완료 {}", userIndex, notificationDTOs);

            return notificationDTOs;
        }
        catch (NonRegistrationUserException ignore){
            throw new NonRegistrationUserException();
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }
    }

    @Transactional
    public NotificationDTO updateNotificationCheck(Long notificationIndex) throws NonRegistrationNotificationException {
        try{
            Notification notification = notificationRepository.findByNotificationIndex(notificationIndex);

            if(notification == null) {
                LOGGER.info(".updateNotificationCheck [err] 존재하지 않는 알림={} 조회", notificationIndex);
                throw new NonRegistrationNotificationException();
            }
            notification.setNotificationCheck(false);
            notification = notificationRepository.save(notification);
            NotificationDTO updateNotificationDTO = EntityConverter.toNotificationDTO(notification);

            LOGGER.info(".updateNotificationCheck 알림 수정 완료 {}", updateNotificationDTO);
            return updateNotificationDTO;
        }
        catch(NonRegistrationNotificationException ignore){
            throw new NonRegistrationNotificationException();
        } catch (Exception ignore){
            throw new TransactionFailedException();
        }

    }


    private void deleteNotification() {
        LocalDate nowTime = LocalDate.now();
        LocalDateTime threeDayAgo = nowTime.minusDays(3).atStartOfDay();
        notificationRepository.deleteByNotificationTimeBefore(threeDayAgo);
        LOGGER.info(".deleteNotification  3일 이전 모든 알림 삭제 완료");
    }


    @Transactional
    @Scheduled(cron="1 0 0 * * *")
    public void settingTodayNotification() {
        LOGGER.info(".settingTodayNotification 알림 일괄 생성 실행");

        // 00시 알림 당일 알림 일괄 생성
        wakeUpTimeNotifications = searchTodayNotification(LocalDate.now(),1);
        morningTimeNotifications = searchTodayNotification(LocalDate.now(),2);
        lunchTimeNotification = searchTodayNotification(LocalDate.now(),3);
        dinnerTimeNotifications = searchTodayNotification(LocalDate.now(),4);
        bedTimeNotifications = searchTodayNotification(LocalDate.now(),5);

        // 3일 이전 알림 내역 삭제
        deleteNotification();
    }

    private List<NotificationContentDTO> searchTodayNotification(LocalDate localDate, int takePillTime){
        List<NotificationContentSummary> notificationContentSummaryList = notificationRepository.findNotificationContentsData(localDate, takePillTime);
        List<NotificationContentDTO> notificationContentDTOS = new ArrayList<>();
        NotificationContentDTO tempNotificationContentDTO;

        List<String> groupMemberName = new ArrayList<>();
        for(NotificationContentSummary n : notificationContentSummaryList){

            if(groupMemberName.contains(n.getGroupMemberName())){
                continue;
            }

            tempNotificationContentDTO = NotificationContentDTO.builder()
                    .userIndex(n.getUserIndex())
                    .userFcmToken(n.getUserFcmToken())
                    .groupMemberName(n.getGroupMemberName())
                    .groupMemberPhone(n.getGroupMemberPhone())
                    .takePillTime(n.getTakePillTime())
                    .build();
            groupMemberName.add(n.getGroupMemberName());
            notificationContentDTOS.add(tempNotificationContentDTO);
        }

        LOGGER.info(".settingTodayNotification  takePillTIme={} 3일 이내 알림내역 조회 완료",takePillTime);
        return notificationContentDTOS;
    }

    @Scheduled(cron="0 30 6 * * *")
    public void sendWakeUpTimeNotification() throws EtcFirebaseException {
        if(!wakeUpTimeNotifications.isEmpty()){

            //  DB 저장용도 notificationList
            List<Notification> notificationList = new ArrayList<>();
            User user = new User();
            wakeUpTimeNotifications.forEach(n -> {
                user.setUserIndex(n.getUserIndex());
                notificationList.add(
                        Notification.builder()
                                .notificationIndex(null)
                                .notificationTime(LocalDateTime.now())
                                .notificationContent(n.getGroupMemberName() + "님 기상약 알림 입니다.")
                                .user(user)
                                .notificationCheck(false)
                                .build()
                );
            });

            // 알림 일괄 전송
            List<Message> messages = new ArrayList<>();
            wakeUpTimeNotifications.forEach(n -> {
                messages.add(
                        Message.builder()
                                .setNotification(FirebaseNotification.builder()
                                        .setTitle("알림")
                                        .setBody(n.getGroupMemberName() + "님 기상약 알림 입니다.")
                                        .build())
                                .putData("유저 인덱스", n.getUserIndex().toString())
                                .putData("그룹원 전화번호", n.getGroupMemberPhone())
                                .setToken(n.getUserFcmToken())
                                .build()
                );
            });

            BatchResponse response;
            try {
                response = FirebaseMessaging.getInstance().sendAll(messages);
                LOGGER.info(".sendWakeUpTimeNotification BedTime 알림 전송 완료 success{}, fail{} :{}", response.getSuccessCount(), response.getFailureCount(), response.getResponses());
            } catch (FirebaseMessagingException e) {
                LOGGER.info(".sendWakeUpTimeNotification [err] fail firebase send notification {}", e.getMessage());
                throw new EtcFirebaseException();
            }

            // notificationDTOList 정보로 알림 전송 후 notification 생성
            notificationRepository.saveAll(notificationList);
            LOGGER.info(".sendWakeUpTimeNotification  WakeUpTime 알림 생성 완료{} ", notificationList);
        }
        else {
            LOGGER.info(".sendWakeUpTimeNotification  전송할 알림이 없습니다.");
        }
    }

    @Scheduled(cron="0 30 8 * * *")
    public void sendMorningTimeNotification () throws EtcFirebaseException {
        if(!morningTimeNotifications.isEmpty()) {
            List<Notification> notificationList = new ArrayList<>();
            User user = new User();
            morningTimeNotifications.forEach(n -> {
                user.setUserIndex(n.getUserIndex());
                notificationList.add(
                        Notification.builder()
                                .notificationIndex(null)
                                .notificationTime(LocalDateTime.now())
                                .notificationContent(n.getGroupMemberName() + "님 아침약 알림 입니다.")
                                .user(user)
                                .notificationCheck(false)
                                .build()
                );
            });

            // 알림 일괄 전송
            List<Message> messages = new ArrayList<>();
            morningTimeNotifications.forEach(n -> {
                messages.add(
                        Message.builder()
                                .setNotification(FirebaseNotification.builder()
                                        .setTitle("알림")
                                        .setBody(n.getGroupMemberName() + "님 아침약 알림 입니다.")
                                        .build())
                                .putData("유저 인덱스", n.getUserIndex().toString())
                                .putData("그룹원 전화번호", n.getGroupMemberPhone())
                                .setToken(n.getUserFcmToken())
                                .build()
                );
            });

            BatchResponse response;
            try {
                response = FirebaseMessaging.getInstance().sendAll(messages);
                LOGGER.info(".sendMorningTimeNotification BedTime 알림 전송 완료 success{}, fail{} :{}", response.getSuccessCount(), response.getFailureCount(), response.getResponses());
            } catch (FirebaseMessagingException e) {
                LOGGER.info(".sendMorningTimeNotification [err] fail firebase send notification {}", e.getMessage());
                throw new EtcFirebaseException();
            }


            // notificationDTOList의 정보로 알림 전송 후 notification 생성
            notificationRepository.saveAll(notificationList);
            LOGGER.info(".sendMorningTimeNotification  MorningTime 알림 생성 완료{} ", notificationList);
        }
        else {
            LOGGER.info(".sendMorningTimeNotification  전송할 알림이 없습니다.");
        }
    }

    @Scheduled(cron="0 30 12 * * *")
    public void sendLunchTimeNotification() throws EtcFirebaseException {
        if(!lunchTimeNotification.isEmpty()) {
            List<Notification> notificationList = new ArrayList<>();
            User user = new User();
            lunchTimeNotification.forEach(n -> {
                user.setUserIndex(n.getUserIndex());
                notificationList.add(
                        Notification.builder()
                                .notificationIndex(null)
                                .notificationTime(LocalDateTime.now())
                                .notificationContent(n.getGroupMemberName() + "님 점심약 알림 입니다.")
                                .user(user)
                                .notificationCheck(false)
                                .build()
                );
            });

            // 알림 일괄 전송
            List<Message> messages = new ArrayList<>();
            lunchTimeNotification.forEach(n -> {
                messages.add(
                        Message.builder()
                                .setNotification(FirebaseNotification.builder()
                                        .setTitle("알림")
                                        .setBody(n.getGroupMemberName() + "님 점심약 알림 입니다.")
                                        .build())
                                .putData("유저 인덱스", n.getUserIndex().toString())
                                .putData("그룹원 전화번호", n.getGroupMemberPhone())
                                .setToken(n.getUserFcmToken())
                                .build()
                );
            });

            BatchResponse response;
            try {
                response = FirebaseMessaging.getInstance().sendAll(messages);
                LOGGER.info(".sendLunchTimeNotification BedTime 알림 전송 완료 success{}, fail{} :{}", response.getSuccessCount(), response.getFailureCount(), response.getResponses());
            } catch (FirebaseMessagingException e) {
                LOGGER.info(".sendLunchTimeNotification [err] fail firebase send notification {}", e.getMessage());
                throw new EtcFirebaseException();
            }


            // notificationDTOList의 정보로 알림 전송 후 notification 생성
            notificationRepository.saveAll(notificationList);
            LOGGER.info(".sendLunchTimeNotification  MorningTime 알림 생성 완료{} ", notificationList);
        }
        else {
            LOGGER.info(".sendLunchTimeNotification  전송할 알림이 없습니다.");
        }
    }

    @Scheduled(cron="0 30 17 * * *")
    public void sendDinnerTimeNotification() throws EtcFirebaseException {
        if(!dinnerTimeNotifications.isEmpty()) {
            List<Notification> notificationList = new ArrayList<>();
            User user = new User();
            dinnerTimeNotifications.forEach(n -> {
                user.setUserIndex(n.getUserIndex());
                notificationList.add(
                        Notification.builder()
                                .notificationIndex(null)
                                .notificationTime(LocalDateTime.now())
                                .notificationContent(n.getGroupMemberName() + "님 저녁약 알림 입니다.")
                                .user(user)
                                .notificationCheck(false)
                                .build()
                );
            });

            // 알림 일괄 전송
            List<Message> messages = new ArrayList<>();
            dinnerTimeNotifications.forEach(n -> {
                messages.add(
                        Message.builder()
                                .setNotification(FirebaseNotification.builder()
                                        .setTitle("알림")
                                        .setBody(n.getGroupMemberName() + "님 저녁약 알림 입니다.")
                                        .build())
                                .putData("유저 인덱스", n.getUserIndex().toString())
                                .putData("그룹원 전화번호", n.getGroupMemberPhone())
                                .setToken(n.getUserFcmToken())
                                .build()
                );
            });

            BatchResponse response;
            try {
                response = FirebaseMessaging.getInstance().sendAll(messages);
                LOGGER.info(".sendDinnerTimeNotification BedTime 알림 전송 완료 success{}, fail{} :{}", response.getSuccessCount(), response.getFailureCount(), response.getResponses());
            } catch (FirebaseMessagingException e) {
                LOGGER.info(".sendDinnerTimeNotification [err] fail firebase send notification {}", e.getMessage());
                throw new EtcFirebaseException();
            }

            // notification 저장
            notificationRepository.saveAll(notificationList);
            LOGGER.info(".sendDinnerTimeNotification  DinnerTime 알림 생성 완료{} ", notificationList);
        }
        else {
            LOGGER.info(".sendDinnerTimeNotification  전송할 알림이 없습니다.");
        }
    }

    @Scheduled(cron="0 30 21 * * *")
    public void sendBedTimeNotification() throws EtcFirebaseException {
        if(!bedTimeNotifications.isEmpty()) {
            List<Notification> notificationList = new ArrayList<>();
            User user = new User();
            bedTimeNotifications.forEach(n -> {
                user.setUserIndex(n.getUserIndex());
                notificationList.add(
                        Notification.builder()
                                .notificationIndex(null)
                                .notificationTime(LocalDateTime.now())
                                .notificationContent(n.getGroupMemberName() + "님 취침약 알림 입니다.")
                                .user(user)
                                .notificationCheck(false)
                                .build()
                );
            });

            // 알림 일괄 전송
            List<Message> messages = new ArrayList<>();
            bedTimeNotifications.forEach(n -> {
                messages.add(
                        Message.builder()
                                .setNotification(FirebaseNotification.builder()
                                        .setTitle("알림")
                                        .setBody(n.getGroupMemberName() + "님 취침약 알림 입니다.")
                                        .build())
                                .putData("유저 인덱스", n.getUserIndex().toString())
                                .putData("그룹원 전화번호", n.getGroupMemberPhone())
                                .setToken(n.getUserFcmToken())
                                .build()
                );
            });

            BatchResponse response;
            try {
                response = FirebaseMessaging.getInstance().sendAll(messages);
                LOGGER.info(".sendBedTimeNotification BedTime 알림 전송 완료 success{}, fail{} :{}", response.getSuccessCount(), response.getFailureCount(), response.getResponses());
            } catch (FirebaseMessagingException e) {
                LOGGER.info(".sendBedTimeNotification [err] fail firebase send notification {}", e.getMessage());
                throw new EtcFirebaseException();
            }

            // notificationDTOList의 정보로 알림 전송 후 notification 생성
            notificationRepository.saveAll(notificationList);
            LOGGER.info(".sendBedTimeNotification  BedTime 알림 생성 완료{} ", notificationList);
        }
        else {
            LOGGER.info(".sendBedTimeNotification  전송할 알림이 없습니다.");
        }
    }

//    @Transactional
//    @Scheduled(cron = "3 */2 * * * *")
//    public void testNotificationDelete(){
//        LocalDateTime nowTime = LocalDateTime.now();
//        LocalDateTime fiveMinutesAgo = nowTime.minusMinutes(1);
//        notificationRepository.deleteByNotificationTimeBefore(fiveMinutesAgo);
//        System.out.println("1분전 알림 삭제 완료");
//    }
//
//    @Transactional
//    @Scheduled(cron="*/10 * * * * *")
//    public void testNotification() throws EtcFirebaseException {
//        try {
//
//            // 복용 알림
//            String phone = "010-1111-1111";
//            String token = "eHhd-ujGRp-xLuGf2DlXr7:APA91bGVi9XhXLHvz7f702ZPxsZX9_kSGig0SqYJK_ELZ_KPX0Uq22c2G4hlre0Dw5oAixaXOTuirSXM0Q3uIMvmZyskaIB9ShLEBscIPBQnqXNz1Eu8D1EFOLvgg59-6dmHdaTMOfLV";
//            String contents = "김주호님 아침약 알림 입니다.";
//            Message message = Message.builder()
//                    .setNotification(com.google.firebase.messaging.Notification.builder()
//                            .setTitle("복용 알림")
//                            .setBody(contents)
//                            .build())
//                    .putData("유저 인덱스", "89")
//                    .putData("그룹원 전화번호", phone)
//                    .setToken(token)
//                    .build();
//
//            com.kit.pillgood.domain.Notification notification = com.kit.pillgood.domain.Notification.builder()
//                    .notificationIndex(null)
//                    .notificationTime(LocalDateTime.now())
//                    .notificationContent(contents)
//                    .user(User.builder().userIndex(89L).build())
//                    .notificationCheck(false)
//                    .build();
//
//            String response;
//            response = FirebaseMessaging.getInstance().send(message);
//            System.out.println("send message" + response);
//
//            notification = notificationRepository.save(notification);
//            System.out.println(notification);
//
//            // OCR 알림
//            contents = "김주호님 OCR 알림 입니다.";
//            List<Integer> timeList = new ArrayList<>();
//            timeList.add(2);
//            timeList.add(1);
//
//            PillScheduleDTO pillScheduleDTO1 = PillScheduleDTO.builder()
//                    .pillName("베아세프정250밀리그람(세푸록심악세틸)(수출명:ZenaTab.)")
//                    .takeDay(2)
//                    .takeCount(1)
//                    .takePillTimeList(timeList)
//                    .build();
//            PillScheduleDTO pillScheduleDTO2 = PillScheduleDTO.builder()
//                    .pillName("페니라민정(클로르페니라민말레산염)")
//                    .takeDay(2)
//                    .takeCount(1)
//                    .takePillTimeList(timeList)
//                    .build();
//            PillScheduleDTO pillScheduleDTO3 = PillScheduleDTO.builder()
//                    .pillName("레보투스정(레보드로프로피진)")
//                    .takeDay(2)
//                    .takeCount(1)
//                    .takePillTimeList(timeList)
//                    .build();
//            PillScheduleDTO pillScheduleDTO4 = PillScheduleDTO.builder()
//                    .pillName("설포라제캡슐(아세브로필린)")
//                    .takeDay(2)
//                    .takeCount(1)
//                    .takePillTimeList(timeList)
//                    .build();
//
//            List<PillScheduleDTO> pillList = new ArrayList<>();
//            pillList.add(pillScheduleDTO1);
//            pillList.add(pillScheduleDTO2);
//            pillList.add(pillScheduleDTO3);
//            pillList.add(pillScheduleDTO4);
//
//            EditOcrDTO editOcrDTO = EditOcrDTO.builder()
//                    .groupMemberIndex(100L)
//                    .groupMemberName("사용자")
//                    .diseaseCode("R05")
//                    .phoneNumber("(054)468-9114")
//                    .startDate(LocalDate.parse("2023-06-07"))
//                    .hospitalName("순천향대학교부속구미병원")
//                    .pillList(pillList)
//                    .build();
//
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            String pillListJson = objectMapper.writeValueAsString(pillList);
//
//            message = Message.builder()
//                    .setNotification(
//                            com.google.firebase.messaging.Notification.builder()
//                            .setTitle("OCR 알림")
//                            .setBody("OCR 등록 완료")
//                            .build()
//                    )
//                    .putData("그룹원 인덱스", editOcrDTO.getGroupMemberIndex().toString())
//                    .putData("그룹원 이름", editOcrDTO.getGroupMemberName())
//                    .putData("복용 시작 날짜", editOcrDTO.getStartDate().toString())
//                    .putData("병원 이름", editOcrDTO.getHospitalName())
//                    .putData("병원 전화번호", editOcrDTO.getPhoneNumber())
//                    .putData("질병 코드", editOcrDTO.getDiseaseCode())
//                    .putData("약 정보", pillListJson)
//                    .setToken(token)
//                    .build();
//
//
//            notification = com.kit.pillgood.domain.Notification.builder()
//                    .notificationIndex(null)
//                    .notificationTime(LocalDateTime.now())
//                    .notificationContent(contents)
//                    .user(User.builder().userIndex(102L).build())
//                    .notificationCheck(false)
//                    .build();
//
//
//            response = FirebaseMessaging.getInstance().send(message);
//            System.out.println("send message" + response);
//
//            notification = notificationRepository.save(notification);
//            System.out.println(notification);
//
//
//        } catch (FirebaseMessagingException e) {
//            throw new EtcFirebaseException();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
