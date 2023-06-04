package com.kit.pillgood.persistence.projection;

public interface NotificationContentSummary {
    Long getUserIndex();
    String getUserFcmToken();
    String getGroupMemberName();
    String getGroupMemberPhone();
    Integer getTakePillTime();
}
