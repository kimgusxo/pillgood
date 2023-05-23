package com.kit.pillgood.persistence.projection;

public interface NotificationContentSummary {
    String getGroupMemberName();
    Integer getTakePillTime();
    Long getUserIndex();
    String getUserFcmToken();
}
