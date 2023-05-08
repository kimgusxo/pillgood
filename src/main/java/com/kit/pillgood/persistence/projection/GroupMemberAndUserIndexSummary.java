package com.kit.pillgood.persistence.projection;

import java.time.LocalDate;

public interface GroupMemberAndUserIndexSummary {
    Long getGroupMemberIndex();
    Long getUserIndex();
    String getGroupMemberName();
    LocalDate getGroupMemberBirth();
    String getGroupMemberPhone();
    Boolean getMessageCheck();

}
