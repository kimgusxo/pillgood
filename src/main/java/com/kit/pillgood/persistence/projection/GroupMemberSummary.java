package com.kit.pillgood.persistence.projection;

import java.time.LocalDate;

public interface GroupMemberSummary {
    Long getGroupMemberIndex();
    String getGroupMemberName();
    LocalDate getGroupMemberBirth();
    String getGroupMemberPhone();
    Boolean getMessageCheck();
}
