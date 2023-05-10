package com.kit.pillgood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="GROUP_MEMBER_INDEX", nullable = false)
    @JsonIgnore
    @NotNull
    private GroupMember groupMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DISEASE_INDEX", nullable = false)
    @JsonIgnore
    @NotNull
    private Disease disease;

    @Builder.Default
    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TakePill> takePills = new ArrayList<>();

    @Column(name = "PRESCRIPTION_REGISTRATION_DATE", nullable = false)
    @NotNull
    private LocalDate prescriptionRegistrationDate;

    @Column(name = "PRESCRIPTION_DATE", nullable = false)
    @NotNull
    private LocalDate prescriptionDate;

    @Column(name = "HOSPITAL_PHONE")
    private String hospitalPhone;

    @Column(name = "HOSPITAL_NAME", nullable = false)
    @NotNull
    private String hospitalName;
}
