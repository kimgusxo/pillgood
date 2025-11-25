package com.kit.pillgood.util;

import com.kit.pillgood.domain.*;
import com.kit.pillgood.persistence.dto.*;
import com.kit.pillgood.persistence.projection.MedicationInfoSummary;
import com.kit.pillgood.persistence.projection.PrescriptionAndDiseaseNameSummary;
import com.kit.pillgood.persistence.projection.TakePillAndTakePillCheckSummary;

import java.time.LocalDate;
import java.util.List;

public class EntityConverter {

    // User
    public static User toUser(UserDTO userDTO) {
        return User.builder()
                .userIndex(userDTO.getUserIndex())
                .userEmail(userDTO.getUserEmail())
                .userFcmToken(userDTO.getUserFcmToken())
                .build();
    }

    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .userIndex(user.getUserIndex())
                .userEmail(user.getUserEmail())
                .userFcmToken(user.getUserFcmToken())
                .build();
    }

    // GroupMember
    public static GroupMember toGroupMember(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) {
        return GroupMember.builder()
                .groupMemberIndex(null)
                .user(User.builder()
                        .userIndex(groupMemberAndUserIndexDTO.getUserIndex())
                        .build())
                .groupMemberName(groupMemberAndUserIndexDTO.getGroupMemberName())
                .groupMemberBirth(groupMemberAndUserIndexDTO.getGroupMemberBirth())
                .groupMemberPhone(groupMemberAndUserIndexDTO.getGroupMemberPhone())
                .messageCheck(groupMemberAndUserIndexDTO.getMessageCheck())
                .build();
    }

    public static GroupMemberAndUserIndexDTO toGroupMemberAndUserIndexDTO(GroupMember groupMember) {
        return GroupMemberAndUserIndexDTO.builder()
                .groupMemberIndex(groupMember.getGroupMemberIndex())
                .userIndex(groupMember.getUser().getUserIndex())
                .groupMemberName(groupMember.getGroupMemberName())
                .groupMemberBirth(groupMember.getGroupMemberBirth())
                .groupMemberPhone(groupMember.getGroupMemberPhone())
                .messageCheck(groupMember.getMessageCheck())
                .build();
    }

    // Notification
    public static NotificationDTO toNotificationDTO(Notification notification) {
        return NotificationDTO.builder()
                .notificationIndex(notification.getNotificationIndex())
                .notificationCheck(notification.getNotificationCheck())
                .notificationContent(notification.getNotificationContent())
                .notificationTime(notification.getNotificationTime())
                .build();
    }

    // Prescription
    public static Prescription toPrescription(Disease disease, EditOcrDTO editOcrDTO) {
        return Prescription.builder()
                .prescriptionIndex(null)
                .groupMember(GroupMember.builder()
                        .groupMemberIndex(editOcrDTO.getGroupMemberIndex())
                        .build())
                .disease(Disease.builder()
                        .diseaseIndex(disease.getDiseaseIndex())
                        .build())
                .prescriptionRegistrationDate(LocalDate.now())
                .prescriptionDate(editOcrDTO.getStartDate())
                .hospitalPhone(editOcrDTO.getPhoneNumber())
                .hospitalName(editOcrDTO.getHospitalName())
                .build();
    }

    public static PrescriptionAndDiseaseNameDTO toPrescriptionAndDiseaseNameDTO(
            PrescriptionAndDiseaseNameSummary prescriptionAndDiseaseNameSummary,
            List<PartiallyTakePillDTO> partiallyTakePillDTOList) {
        return PrescriptionAndDiseaseNameDTO.builder()
                .prescriptionIndex(prescriptionAndDiseaseNameSummary.getPrescriptionIndex())
                .groupMemberIndex(prescriptionAndDiseaseNameSummary.getGroupMemberIndex())
                .diseaseIndex(prescriptionAndDiseaseNameSummary.getDiseaseIndex())
                .prescriptionRegistrationDate(prescriptionAndDiseaseNameSummary.getPrescriptionRegistrationDate())
                .prescriptionDate(prescriptionAndDiseaseNameSummary.getPrescriptionDate())
                .hospitalPhone(prescriptionAndDiseaseNameSummary.getHospitalPhone())
                .hospitalName(prescriptionAndDiseaseNameSummary.getHospitalName())
                .diseaseName(prescriptionAndDiseaseNameSummary.getDiseaseName())
                .partiallyTakePillDTOList(partiallyTakePillDTOList)
                .build();
    }

    // TakePill
    public static TakePill toTakePill(Long prescriptionIndex, Pill pill, PillScheduleDTO pillScheduleDTO) {
        return TakePill.builder()
                .takePillIndex(null)
                .prescription(Prescription.builder()
                        .prescriptionIndex(prescriptionIndex)
                        .build())
                .pill(Pill.builder()
                        .pillIndex(pill.getPillIndex())
                        .build())
                .takePillCheck(null)
                .takeDay(pillScheduleDTO.getTakeDay())
                .takeCount(pillScheduleDTO.getTakeCount())
                .build();
    }

    public static TakePillAndTakePillCheckDTO toTakePillAndTakePillCheckDTO(TakePillAndTakePillCheckSummary takePillAndTakePillCheckSummary) {
        return TakePillAndTakePillCheckDTO.builder()
        .takePillIndex(takePillAndTakePillCheckSummary.getTakePillIndex())
        .prescriptionIndex(takePillAndTakePillCheckSummary.getPrescriptionIndex())
        .pillIndex(takePillAndTakePillCheckSummary.getPillIndex())
        .takeDay(takePillAndTakePillCheckSummary.getTakeDay())
        .takeCount(takePillAndTakePillCheckSummary.getTakeCount())
        .takePillCheckIndex(takePillAndTakePillCheckSummary.getTakePillCheckIndex())
        .takeDate(takePillAndTakePillCheckSummary.getTakeDate())
        .takePillTime(takePillAndTakePillCheckSummary.getTakePillTime())
        .takeCheck(takePillAndTakePillCheckSummary.getTakeCheck())
        .build();
    }

    public static TakePillAndTakePillCheckAndGroupMemberIndexDTO toTakePillAndTakePillCheckAndGroupMemberIndexDTO(GroupMember groupMember, List<TakePillAndTakePillCheckDTO> takePillAndTakePillCheckDTOs) {
        return TakePillAndTakePillCheckAndGroupMemberIndexDTO.builder()
                .groupMemberIndex(groupMember.getGroupMemberIndex())
                .takePillAndTakePillCheckDTOs(takePillAndTakePillCheckDTOs)
                .build();
    }

    // TakePillCheck
    public static TakePillCheck toTakePillCheck(Long takePillIndex, Integer takePillTime, EditOcrDTO editOcrDTO) {
        return TakePillCheck.builder()
                .takePillCheckIndex(null)
                .takePill(TakePill.builder()
                        .takePillIndex(takePillIndex)
                        .build())
                .takeDate(editOcrDTO.getStartDate())
                .takePillTime(takePillTime)
                .takeCheck(false)
                .build();
    }

    // Disease
    public static DiseaseDTO toDiseaseDTO(Disease disease) {
        return DiseaseDTO.builder()
                .diseaseIndex(disease.getDiseaseIndex())
                .diseaseCode(disease.getDiseaseCode())
                .diseaseName(disease.getDiseaseName())
                .build();
    }

    // Pill
    public static PillDTO toPillDTO(Pill pill) {
        return PillDTO.builder()
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
    }

    // MedicationInfo
    public static MedicationInfoDTO toMedicationInfo(MedicationInfoSummary medicationInfoSummary) {
        return MedicationInfoDTO.builder()
                .groupMemberIndex(medicationInfoSummary.getGroupMemberIndex())
                .groupMemberName(medicationInfoSummary.getGroupMemberName())
                .pillIndex(medicationInfoSummary.getPillIndex())
                .pillNum(medicationInfoSummary.getPillNum())
                .pillFrontWord(medicationInfoSummary.getPillFrontWord())
                .pillBackWord(medicationInfoSummary.getPillBackWord())
                .pillShape(medicationInfoSummary.getPillShape())
                .pillColor(medicationInfoSummary.getPillColor())
                .pillCategoryName(medicationInfoSummary.getPillCategoryName())
                .pillFormulation(medicationInfoSummary.getPillFormulation())
                .pillEffect(medicationInfoSummary.getPillEffect())
                .pillPrecaution(medicationInfoSummary.getPillPrecaution())
                .pillName(medicationInfoSummary.getPillName())
                .diseaseIndex(medicationInfoSummary.getDiseaseIndex())
                .diseaseName(medicationInfoSummary.getDiseaseName())
                .takePillCheckIndex(medicationInfoSummary.getTakePillCheckIndex())
                .takeCheck(medicationInfoSummary.getTakeCheck())
                .takePillTime(medicationInfoSummary.getTakePillTime())
                .build();
    }

    // EditOcr
    public static EditOcrDTO toEditOcrDTO(Long groupMemberIndex, String groupMemberName, LocalDate dateStart, OriginalOcrDTO originalOcrDTO) {
        return EditOcrDTO.builder()
                .groupMemberIndex(groupMemberIndex)
                .groupMemberName(groupMemberName)
                .startDate(dateStart)
                .hospitalName(originalOcrDTO.getHospitalName())
                .phoneNumber(originalOcrDTO.getPhoneNumber())
                .diseaseCode(originalOcrDTO.getDiseaseCode())
                .pillList(originalOcrDTO.getPillNameList())
                .build();
    }
}
