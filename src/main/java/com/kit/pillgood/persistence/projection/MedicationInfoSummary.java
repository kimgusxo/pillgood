package com.kit.pillgood.persistence.projection;

public interface MedicationInfoSummary {
    Long getGroupMemberIndex();
    String getGroupMemberName();
    Long getPillIndex();
    String getPillNum();
    String getPillName();
    String getPillFrontWord();
    String getPillBackWord();
    String getPillShape();
    String getPillColor();
    String getPillCategoryName();
    String getPillFormulation();
    String getPillEffect();
    String getPillPrecaution();
    Long getDiseaseIndex();
    String getDiseaseName();
    Long getTakePillCheckIndex();
    Boolean getTakeCheck();
    Integer getTakePillTime();
}
