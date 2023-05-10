package com.kit.pillgood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TakePillCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAKE_PILL_CHECK_INDEX")
    private Long takePillCheckIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="TAKE_PILL_INDEX", nullable = false)
    @NotNull
    private TakePill takePill;

    @Column(name = "TAKE_DATE", nullable = false)
    @NotNull
    private LocalDate takeDate;

    @Column(name = "TAKE_PILL_TIME", nullable = false)
    @NotNull
    private Integer takePillTime;

    @Column(name = "TAKE_CHECK", nullable = false)
    @NotNull
    private Boolean takeCheck;
}
