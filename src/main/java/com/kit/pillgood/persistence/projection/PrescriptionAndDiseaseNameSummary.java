package com.kit.pillgood.persistence.projection;

import com.kit.pillgood.domain.GroupMember;

import java.time.LocalDate;

public interface PrescriptionAndDiseaseNameSummary {
    Long getPrescriptionIndex();
    Long getGroupMemberIndex();
    LocalDate getPrescriptionRegistrationDate();
    LocalDate getPrescriptionDate();
    String getHospitalPhone();
    String getHospitalName();
    String getDiseaseName();
}
