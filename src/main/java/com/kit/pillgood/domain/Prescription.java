package com.kit.pillgood.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRESCRIPTION_INDEX")
    private Long prescriptionIndex;

    @ManyToOne
    @JoinColumn(name="GROUP_MEMBER_INDEX")
    private GroupMember groupMember;

    @ManyToOne
    @JoinColumn(name="DISEASE_INDEX")
    private Disease disease;

    @Builder.Default
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TakePill> takePills = new ArrayList<>();

    @Column(name = "PRESCRIPTION_REGISTRATION_DATE")
    @NonNull
    private LocalDate prescriptionRegistrationDate;

    @Column(name = "PRESCRIPTION_DATE")
    @NonNull
    private LocalDate prescriptionDate;

    @Column(name = "HOSPITAL_PHONE")
    private String hospitalPhone;

    @Column(name = "HOSPITAL_NAME")
    @NonNull
    private String hospitalName;
}
