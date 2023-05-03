package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_INDEX")
    private Long notificationIndex;

    @ManyToOne
    @JoinColumn(name="USER_INDEX")
    private User user;

    @Column(name = "NOTIFICATION_CONTENT")
    @NonNull
    private String notificationContent;

    @Column(name = "NOTIFICATION_TIME")
    private LocalDateTime notificationTime;

    @Column(name = "NOTIFICATION_CHECK")
    @NonNull
    private boolean notificationCheck;

}
