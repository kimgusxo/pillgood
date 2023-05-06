package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TakePill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAKE_PILL_INDEX")
    private Long takePillIndex;

    @ManyToOne
    @JoinColumn(name="PRESCRIPTION_INDEX", nullable = false)
    @NotNull
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name="PILL_INDEX", nullable = false)
    @NotNull
    private Pill pill;

    @Builder.Default
    @OneToMany(mappedBy = "takePill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TakePillCheck> takePillCheck = new ArrayList<>();

    @Column(name = "TAKE_DAY", nullable = false)
    @NotNull
    private Integer takeDay;

    @Column(name = "TAKE_COUNT", nullable = false)
    @NotNull
    private Integer takeCount;
}
