package com.kit.pillgood.persistence.projection;

import java.time.LocalDate;
import java.util.List;

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
