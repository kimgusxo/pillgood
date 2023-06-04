package com.kit.pillgood.service;

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
import com.kit.pillgood.persistence.dto.NotificationContentDTO;
import com.kit.pillgood.persistence.dto.NotificationDTO;
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
            List<Notification> notifications = notificationRepository.findNotificationByUser_UserIndexAndNotificationTimeAfterAndNotificationCheckFalse(userIndex, threeDayAgo);

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
    public NotificationDTO updateNotificationCheck(Long notificationIndex) throws NonRegistrationNotificationException, NonRegistrationUserException {
        try{
            Notification notification = notificationRepository.findByNotificationIndex(notificationIndex);

            if(notification == null) {
                LOGGER.info(".updateNotificationCheck [err] 존재하지 않는 알림={} 조회", notificationIndex);
                throw new NonRegistrationNotificationException();
            }

            NotificationDTO updateNotificationDTO = settingUpdateNotificationData(notification);

            NotificationDTO newNotificationDTO = createUpdateNotification(updateNotificationDTO);
            deleteUpdateNotification(notificationIndex);

            LOGGER.info(".updateNotificationCheck 알림 수정 완료 {}", newNotificationDTO);
            return newNotificationDTO;
        }
        catch(NonRegistrationNotificationException ignore){
            throw new NonRegistrationNotificationException();
        }
        catch (NonRegistrationUserException ignore){
            throw new NonRegistrationUserException();
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }

    }

    private NotificationDTO settingUpdateNotificationData(Notification notification){
        return NotificationDTO.builder()
                .notificationIndex(null)
                .notificationTime(notification.getNotificationTime())
                .notificationContent(notification.getNotificationContent())
                .userIndex(notification.getUser().getUserIndex())
                .notificationCheck(true)
                .build();
    }

    private void deleteUpdateNotification(Long notificationIndex) throws NonRegistrationNotificationException {
        if(!notificationRepository.existsByNotificationIndex(notificationIndex)){
            LOGGER.info(".deleteNotification [err] 존재하지 않는 notificationIndex={} 조회", notificationIndex);
            throw new NonRegistrationNotificationException();
        }
        notificationRepository.deleteByNotificationIndex(notificationIndex);
        LOGGER.info(".deleteNotification 그룹원 삭제 완료 groupMemberIndex={}", notificationIndex);
    }

    private void deleteNotification() {
        LocalDate nowTime = LocalDate.now();
        LocalDateTime threeDayAgo = nowTime.minusDays(3).atStartOfDay();
        notificationRepository.deleteByNotificationTimeBefore(threeDayAgo);
        LOGGER.info(".deleteNotification  3일 이전 모든 알림 삭제 완료");

    }

    private NotificationDTO createUpdateNotification(NotificationDTO notificationDTO) throws NonRegistrationUserException {
        Long userIndex = notificationDTO.getUserIndex();

        if(!userRepository.existsByUserIndex(userIndex)){
            LOGGER.info(".createNotification [err] 존재하지 않은 userIndex={} 검색", userIndex);
            throw new NonRegistrationUserException();
        }

        User user = User.builder()
                .userIndex(userIndex)
                .build();

        Notification notification = Notification.builder()
                .notificationIndex(null)
                .notificationTime(LocalDateTime.now())
                .notificationContent(notificationDTO.getNotificationContent())
                .user(user)
                .notificationCheck(true)
                .build();


        Notification newNotification = notificationRepository.save(notification);
        NotificationDTO newNotificationDTO = EntityConverter.toNotificationDTO(newNotification);

        LOGGER.info(".createNotification 알림 생성 완료{}", newNotificationDTO);

        return newNotificationDTO;
    }

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
                    .groupMemberName(n.getGroupMemberName())
                    .takePillTime(n.getTakePillTime())
                    .userFcmToken(n.getUserFcmToken())
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
}
