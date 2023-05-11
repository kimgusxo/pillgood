package com.kit.pillgood.persistence.projection;

import java.time.LocalDate;
public interface TakePillAndTakePillCheckSummary {
     Long getTakePillIndex();
     Long getPrescriptionIndex();
     Long getPillIndex();
     Integer getTakeDay();
     Integer getTakeCount();
     Long getTakePillCheckIndex();
     LocalDate getTakeDate();
     Integer getTakePillTime();
     Boolean getTakeCheck();
}
