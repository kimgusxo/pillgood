package com.kit.pillgood.persistence.projection;

public interface MedicationInfoSummary {
    Long getGroupMemberIndex();
    String getGroupMemberName();
    Long getPillIndex();
    String getPillName();
    Long getDiseaseIndex();
    String getDiseaseName();
    Long getTakePillCheckIndex();
    Boolean getTakeCheck();
    Integer getTakePillTime();

}
