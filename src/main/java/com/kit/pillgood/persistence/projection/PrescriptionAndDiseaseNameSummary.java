package com.kit.pillgood.persistence.projection;

import java.time.LocalDate;

public interface PrescriptionAndDiseaseNameSummary {
    Long getPrescriptionIndex();
    Long getGroupMemberIndex();
    Long getDiseaseIndex();
    LocalDate getPrescriptionRegistrationDate();
    LocalDate getPrescriptionDate();
    String getHospitalPhone();
    String getHospitalName();
    String getDiseaseName();
}