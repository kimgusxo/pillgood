package com.kit.pillgood.service;

import com.kit.pillgood.domain.Notification;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.NonRegistrationNotificationException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.NotificationDTO;
import com.kit.pillgood.repository.NotificationRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Service
public class NotificationService {

    private final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public void createAllNotification() {
        // 모든 알림 생성
    }

    /**
     * 현재 날짜 기준 3일 이후의 유저 알림 내역 반환 메소드
     * @param: Long userIndex
     * @return: List<NotificationDTO>
    **/
    @Transactional
    public List<NotificationDTO> searchNotificationByUserIndex(Long userIndex) throws NonRegistrationUserException {

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

    /**
     * notification_check를 True로 변경하는 메소드
     * @param: Long notificationIndex
     * @return: boolean
     **/
    @Transactional
    public NotificationDTO updateNotificationCheck(Long notificationIndex) throws NonRegistrationNotificationException, NonRegistrationUserException {
        Notification notification = notificationRepository.findByNotificationIndex(notificationIndex);

        if(notification == null) {
            LOGGER.info(".updateNotificationCheck [err] 존재하지 않는 알림={} 조회", notificationIndex);
            throw new NonRegistrationNotificationException();
        }

        NotificationDTO updateNotificationDTO = settingUpdateNotificationData(notification);

        NotificationDTO newNotificationDTO = createUpdateNotification(updateNotificationDTO);
        deleteNotification(notificationIndex);

        LOGGER.info(".updateNotificationCheck 알림 수정 완료 {}", newNotificationDTO);
        return newNotificationDTO;

    }


    /**
     * updateNotificationCheck에서 변경될 값을 생성하는 메소드
     * @param: Notification
     * @return: NotificationDTO
     **/
    private NotificationDTO settingUpdateNotificationData(Notification notification){
        return NotificationDTO.builder()
                .notificationIndex(null)
                .notificationTime(notification.getNotificationTime())
                .notificationContent(notification.getNotificationContent())
                .userIndex(notification.getUser().getUserIndex())
                .notificationCheck(true)
                .build();
    }

    /**
     * 알림을 삭제하는 메소드
     * @param: 삭제할 groupMemberIndex
     * @return: 리턴 없음
     **/

    private void deleteNotification(Long notificationIndex) throws NonRegistrationNotificationException {
        if(!notificationRepository.existsByNotificationIndex(notificationIndex)){
            LOGGER.info(".deleteNotification [err] 존재하지 않는 notificationIndex={} 조회", notificationIndex);
            throw new NonRegistrationNotificationException();
        }
        notificationRepository.deleteByNotificationIndex(notificationIndex);
        LOGGER.info(".deleteNotification 그룹원 삭제 완료 groupMemberIndex={}", notificationIndex);
    }

    /**
     * 알림 update를 위한 알림 생성 메소드
     * @param: 생성할 NotificaionDTO
     * @return: 생성된 NotificationDTO
     **/
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

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public void sendAutoPushMessageNotification() {
        // 자동 메세지 전송
    }

    /**
     * 00시 당일 알림 일괄 생성
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/

//    @Scheduled(cron="1 0 0 * * *")
//    public void settingTodayNotification() {
//        // 00시 알림 당일 알림 일괄 생성
//        Long userIndex = notificationDTO.getUserIndex();
//
//        User user = User.builder()
//                .userIndex(userIndex)
//                .build();
//
//        Notification notification = Notification.builder()
//                .notificationIndex(null)
//                .notificationTime(LocalDateTime.now())
//                .notificationContent(notificationDTO.getNotificationContent())
//                .user(user)
//                .notificationCheck(true)
//                .build();
//
//
//        Notification newNotification = notificationRepository.save(notification);
//        NotificationDTO newNotificationDTO = EntityConverter.toNotificationDTO(newNotification);
//
//        LOGGER.info(".createNotification 알림 생성 완료{}", newNotificationDTO);
//    }

    /**
     * 06시 30분 기상 시간 알림 전송
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
     **/
    @Scheduled(cron="0 30 6 * * *")
    public void sendWakeUpTimeNotification(){
        System.out.println(new Date() + " Cron test");
    }

    /**
     * 8시 30분 아침 시간 알림 전송
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
     **/
    @Scheduled(cron="0 30 8 * * *")
    public void sendMorningTimeNotification (){
        System.out.println(new Date() + " Cron test");
    }

    /**
     * 12시 30분 점심 시간 알림 전송
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
     **/
    @Scheduled(cron="0 30 12 * * *")
    public void sendLunchTimeNotification(){
        System.out.println(new Date() + " Cron test");
    }

    /**
     * 17시 30분 저녁 시간 알림 전송
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
     **/
    @Scheduled(cron="0 30 17 * * *")
    public void sendDinnerTimeNotification(){
        System.out.println(new Date() + " Cron test");
    }

    /**
     * 21시 30분 취침 시간 알림 전송
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
     **/
    @Scheduled(cron="0 30 21 * * *")
    public void sendBedTimeNotification(){
        System.out.println(new Date() + " Cron test");

    }
}
