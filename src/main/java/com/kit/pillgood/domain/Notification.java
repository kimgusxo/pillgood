package com.kit.pillgood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_INDEX")
    private Long notificationIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="USER_INDEX", nullable = false)
    @NotNull
    private User user;

    @Column(name = "NOTIFICATION_CONTENT")
    private String notificationContent;

    @Column(name = "NOTIFICATION_TIME")
    private LocalDateTime notificationTime;

    @Column(name = "NOTIFICATION_CHECK")
    private Boolean notificationCheck;

}
