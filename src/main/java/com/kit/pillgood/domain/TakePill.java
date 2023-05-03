package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class TakePill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAKE_PILL_INDEX")
    private Long takePillIndex;

    @ManyToOne
    @JoinColumn(name="PRESCRIPTION_INDEX")
    @NonNull
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name="PILL_INDEX")
    @NonNull
    private Pill pill;

    @OneToMany(mappedBy = "takePill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TakePillCheck> takePillCheck = new ArrayList<>();

    @Column(name = "TAKE_DAY")
    @NonNull
    private Integer takeDay;

    @Column(name = "TAKE_COUNT")
    @NonNull
    private Integer takeCount;
}
