package com.kit.pillgood.util;

import com.kit.pillgood.domain.*;
import com.kit.pillgood.persistence.dto.*;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;

public class EntityConverter {
    // Group
    public static GroupMember toGroupMember(GroupMemberDTO groupMemberDTO) {
        GroupMember groupMember = GroupMember.builder()
                .groupMemberIndex(groupMemberDTO.getGroupMemberIndex())
                .groupMemberName(groupMemberDTO.getGroupMemberName())
                .groupMemberBirth(groupMemberDTO.getGroupMemberBirth())
                .groupMemberPhone(groupMemberDTO.getGroupMemberPhone())
                .messageCheck(groupMemberDTO.getMessageCheck())
                .build();
        return groupMember;
    }

    public static GroupMemberDTO toGroupMemberDTO(GroupMember groupMember) {
        GroupMemberDTO groupMemberDTO = GroupMemberDTO.builder()
                .groupMemberIndex(groupMember.getGroupMemberIndex())
                .userIndex(null)
                .groupMemberName(groupMember.getGroupMemberName())
                .groupMemberBirth(groupMember.getGroupMemberBirth())
                .groupMemberPhone(groupMember.getGroupMemberPhone())
                .messageCheck(groupMember.getMessageCheck())
                .build();
        return groupMemberDTO;
    }

    // Disease
    public static Disease toDisease(DiseaseDTO diseaseDTO) {
        Disease disease = Disease.builder()
                .diseaseIndex(diseaseDTO.getDiseaseIndex())
                .diseaseClassification(diseaseDTO.getDiseaseClassification())
                .diseaseCode(diseaseDTO.getDiseaseCode())
                .diseaseName(diseaseDTO.getDiseaseName())
                .build();

        return disease;
    }

    public static DiseaseDTO toDiseaseDTO(Disease disease) {
        DiseaseDTO diseaseDTO = DiseaseDTO.builder()
                .diseaseIndex(disease.getDiseaseIndex())
                .diseaseCode(disease.getDiseaseCode())
                .diseaseName(disease.getDiseaseName())
                .diseaseClassification(disease.getDiseaseClassification())
                .build();

        return diseaseDTO;
    }

    // Notification
    public static Notification toNotification(NotificationDTO notificationDTO) {
        Notification notification = Notification.builder()
                .notificationIndex(notificationDTO.getNotificationIndex())
                .notificationCheck(notificationDTO.isNotificationCheck())
                .notificationContent(notificationDTO.getNotificationContent())
                .notificationTime(notificationDTO.getNotificationTime())
                .build();

        return notification;
    }

    public static NotificationDTO toNotificationDTO(Notification notification) {
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .notificationIndex(notification.getNotificationIndex())
                .notificationCheck(notification.isNotificationCheck())
                .notificationContent(notification.getNotificationContent())
                .notificationTime(notification.getNotificationTime())
                .build();

        return notificationDTO;
    }

    // Pill
    public static Pill toPill(PillDTO pillDTO) {
        Pill pill = Pill.builder()
                .pillIndex(pillDTO.getPillIndex())
                .pillBackWord(pillDTO.getPillBackWord())
                .pillNum(pillDTO.getPillNum())
                .pillEffect(pillDTO.getPillEffect())
                .pillFormulation(pillDTO.getPillFormulation())
                .pillShape(pillDTO.getPillShape())
                .pillColor(pillDTO.getPillColor())
                .pillName(pillDTO.getPillName())
                .pillPrecaution(pillDTO.getPillPrecaution())
                .pillCategoryName(pillDTO.getPillCategoryName())
                .pillFrontWord(pillDTO.getPillFrontWord())
                .build();

        return pill;
    }

    public static PillDTO toPillDTO(Pill pill) {
        PillDTO pillDTO = PillDTO.builder()
                .pillIndex(pill.getPillIndex())
                .pillBackWord(pill.getPillBackWord())
                .pillNum(pill.getPillNum())
                .pillEffect(pill.getPillEffect())
                .pillFormulation(pill.getPillFormulation())
                .pillShape(pill.getPillShape())
                .pillColor(pill.getPillColor())
                .pillName(pill.getPillName())
                .pillPrecaution(pill.getPillPrecaution())
                .pillCategoryName(pill.getPillCategoryName())
                .pillFrontWord(pill.getPillFrontWord())
                .build();

        return pillDTO;
    }

    // Prescription
    public static Prescription toPrescription(PrescriptionDTO prescriptionDTO) {
        Prescription prescription = Prescription.builder()
                .prescriptionIndex(prescriptionDTO.getPrescriptionIndex())
                .prescriptionDate(prescriptionDTO.getPrescriptionDate())
                .prescriptionRegistrationDate(prescriptionDTO.getPrescriptionRegistrationDate())
                .hospitalName(prescriptionDTO.getHospitalName())
                .hospitalPhone(prescriptionDTO.getHospitalPhone())
                .prescriptionDate(prescriptionDTO.getPrescriptionDate())
                .build();

        return prescription;
    }

    public static PrescriptionDTO toPrescriptionDTO(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = PrescriptionDTO.builder()
                .prescriptionIndex(prescription.getPrescriptionIndex())
                .diseaseIndex(null)
                .groupMemberIndex(null)
                .hospitalName(prescription.getHospitalName())
                .hospitalPhone(prescription.getHospitalPhone())
                .prescriptionDate(prescription.getPrescriptionDate())
                .prescriptionRegistrationDate(prescription.getPrescriptionRegistrationDate())
                .prescriptionDate(prescription.getPrescriptionDate())
                .build();

        return prescriptionDTO;
    }

    // TakePillCheck
    public static TakePillCheck toTakePillCheck(TakePillCheckDTO takePillCheckDTO) {
        TakePillCheck takePillCheck = TakePillCheck.builder()
                .takeCheck(takePillCheckDTO.getTakeCheck())
                .takePillCheckIndex(takePillCheckDTO.getTakePillCheckIndex())
                .takePillTime(takePillCheckDTO.getTakePillTime())
                .build();

        return takePillCheck;
    }

    public static TakePillCheckDTO toTakePillCheckDTO(TakePillCheck takePillCheck) {
        TakePillCheckDTO takePillCheckDTO = TakePillCheckDTO.builder()
                .takeCheck(takePillCheck.getTakeCheck())
                .takePillCheckIndex(takePillCheck.getTakePillCheckIndex())
                .takePillIndex(null)
                .takePillTime(takePillCheck.getTakePillTime())
                .build();

        return takePillCheckDTO;
    }

    // TakePill
    public static TakePill toTakePill(TakePillDTO takePillDTO) {
        TakePill takePill = TakePill.builder()
                .takePillIndex(takePillDTO.getTakePillIndex())
                .takeDay(takePillDTO.getTakeDay())
                .takeCount(takePillDTO.getTakeCount())
                .build();

        return takePill;
    }

    public static TakePillDTO toTakePillDTO(TakePill takePill) {
        TakePillDTO takePillDTO = TakePillDTO.builder()
                .takePillIndex(takePill.getTakePillIndex())
                .pillIndex(null)
                .takeDay(takePill.getTakeDay())
                .takeCount(takePill.getTakeCount())
                .prescriptionIndex(null)
                .build();

        return takePillDTO;
    }

    // User
    public static User toUser(UserDTO userDTO) {
        User user = User.builder()
                .userIndex(userDTO.getUserIndex())
                .userEmail(userDTO.getUserEmail())
                .userFcmToken(userDTO.getUserFcmToken())
                .build();

        return user;
    }

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .userIndex(user.getUserIndex())
                .userEmail(user.getUserEmail())
                .userFcmToken(user.getUserFcmToken())
                .build();

        return userDTO;
    }

    public static PrescriptionAndDiseaseNameDTO toPrescriptionAndDiseaseNameDTO(PrescriptionAndDiseaseNameSummary prescriptionAndDiseaseNameSummary) {
        PrescriptionAndDiseaseNameDTO prescriptionAndDiseaseNameDTO = PrescriptionAndDiseaseNameDTO.builder()
                .prescriptionIndex(prescriptionAndDiseaseNameSummary.getPrescriptionIndex())
                .groupMemberIndex(prescriptionAndDiseaseNameSummary.getGroupMemberIndex())
                .diseaseIndex(prescriptionAndDiseaseNameSummary.getDiseaseIndex())
                .prescriptionRegistrationDate(prescriptionAndDiseaseNameSummary.getPrescriptionRegistrationDate())
                .prescriptionDate(prescriptionAndDiseaseNameSummary.getPrescriptionDate())
                .hospitalPhone(prescriptionAndDiseaseNameSummary.getHospitalPhone())
                .hospitalName(prescriptionAndDiseaseNameSummary.getHospitalName())
                .diseaseName(prescriptionAndDiseaseNameSummary.getDiseaseName())
                .build();

        return prescriptionAndDiseaseNameDTO;
    }

    public static TakePillAndTakePillCheckDTO toTakePillAndTakePillCheckDTO() {
        return null;
    }

    public static TakePillAndTakePillCheckAndGroupMemberIndexDTO toTakePillAndTakePillCheckAndGroupMemberIndexDTO() {
        return null;
    }

}
