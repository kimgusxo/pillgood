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

    // GroupMember
    public static GroupMember toGroupMember(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) {
        GroupMember groupMember = GroupMember.builder()
                .groupMemberIndex(null)
                .user(User.builder()
                        .userIndex(groupMemberAndUserIndexDTO.getUserIndex())
                        .build())
                .groupMemberName(groupMemberAndUserIndexDTO.getGroupMemberName())
                .groupMemberBirth(groupMemberAndUserIndexDTO.getGroupMemberBirth())
                .groupMemberPhone(groupMemberAndUserIndexDTO.getGroupMemberPhone())
                .messageCheck(groupMemberAndUserIndexDTO.getMessageCheck())
                .build();

        return groupMember;
    }

    public static GroupMemberAndUserIndexDTO toGroupMemberAndUserIndexDTO(GroupMember groupMember) {
        GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO = GroupMemberAndUserIndexDTO.builder()
                .groupMemberIndex(groupMember.getGroupMemberIndex())
                .userIndex(groupMember.getUser().getUserIndex())
                .groupMemberName(groupMember.getGroupMemberName())
                .groupMemberBirth(groupMember.getGroupMemberBirth())
                .groupMemberPhone(groupMember.getGroupMemberPhone())
                .messageCheck(groupMember.getMessageCheck())
                .build();
        return groupMemberAndUserIndexDTO;
    }

    // Notification
    public static NotificationDTO toNotificationDTO(Notification notification) {
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .notificationIndex(notification.getNotificationIndex())
                .notificationCheck(notification.getNotificationCheck())
                .notificationContent(notification.getNotificationContent())
                .notificationTime(notification.getNotificationTime())
                .build();

        return notificationDTO;
    }

    // Prescription
    public static Prescription toPrescription(Disease disease, EditOcrDTO editOcrDTO) {
        Prescription prescription = Prescription.builder()
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
        return prescription;
    }

    public static PrescriptionAndDiseaseNameDTO toPrescriptionAndDiseaseNameDTO(
            PrescriptionAndDiseaseNameSummary prescriptionAndDiseaseNameSummary,
            List<PartiallyTakePillDTO> partiallyTakePillDTOList) {
        PrescriptionAndDiseaseNameDTO prescriptionAndDiseaseNameDTO = PrescriptionAndDiseaseNameDTO.builder()
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

        return prescriptionAndDiseaseNameDTO;
    }

    // TakePill
    public static TakePill toTakePill(Long prescriptionIndex, Pill pill, PillScheduleDTO pillScheduleDTO) {
        TakePill takePill = TakePill.builder()
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

        return takePill;
    }

    public static TakePillAndTakePillCheckDTO toTakePillAndTakePillCheckDTO(TakePillAndTakePillCheckSummary takePillAndTakePillCheckSummary) {
        TakePillAndTakePillCheckDTO takePillAndTakePillCheckDTO
                = TakePillAndTakePillCheckDTO.builder()
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
        return takePillAndTakePillCheckDTO;
    }

    public static TakePillAndTakePillCheckAndGroupMemberIndexDTO toTakePillAndTakePillCheckAndGroupMemberIndexDTO(GroupMember groupMember, List<TakePillAndTakePillCheckDTO> takePillAndTakePillCheckDTOs) {
        TakePillAndTakePillCheckAndGroupMemberIndexDTO takePillAndTakePillCheckAndGroupMemberIndexDTO =
                TakePillAndTakePillCheckAndGroupMemberIndexDTO.builder()
                        .groupMemberIndex(groupMember.getGroupMemberIndex())
                        .takePillAndTakePillCheckDTOs(takePillAndTakePillCheckDTOs)
                        .build();
        return takePillAndTakePillCheckAndGroupMemberIndexDTO;
    }

    // TakePillCheck
    public static TakePillCheck toTakePillCheck(Long takePillIndex, Integer takePillTime, EditOcrDTO editOcrDTO) {
        TakePillCheck takePillCheck = TakePillCheck.builder()
                .takePillCheckIndex(null)
                .takePill(TakePill.builder()
                        .takePillIndex(takePillIndex)
                        .build())
                .takeDate(editOcrDTO.getStartDate())
                .takePillTime(takePillTime)
                .takeCheck(false)
                .build();
        return takePillCheck;
    }

    // Disease
    public static DiseaseDTO toDiseaseDTO(Disease disease) {
        DiseaseDTO diseaseDTO = DiseaseDTO.builder()
                .diseaseIndex(disease.getDiseaseIndex())
                .diseaseCode(disease.getDiseaseCode())
                .diseaseName(disease.getDiseaseName())
                .build();

        return diseaseDTO;
    }

    // Pill
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

    // MedicationInfo
    public static MedicationInfoDTO toMedicationInfo(MedicationInfoSummary medicationInfoSummary) {
        MedicationInfoDTO medicationInfoDTO = MedicationInfoDTO.builder()
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
        return medicationInfoDTO;
    }

    // EditOcr
    public static EditOcrDTO toEditOcrDTO(Long groupMemberIndex, String groupMemberName, LocalDate dateStart, OriginalOcrDTO originalOcrDTO) {
        EditOcrDTO editOcrDTO = EditOcrDTO.builder()
                .groupMemberIndex(groupMemberIndex)
                .groupMemberName(groupMemberName)
                .startDate(dateStart)
                .hospitalName(originalOcrDTO.getHospitalName())
                .phoneNumber(originalOcrDTO.getPhoneNumber())
                .diseaseCode(originalOcrDTO.getDiseaseCode())
                .pillList(originalOcrDTO.getPillNameList())
                .build();
        return editOcrDTO;
    }
}
