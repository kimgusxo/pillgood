package com.kit.pillgood.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_MEMBER_INDEX")
    private Long groupMemberIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_INDEX", nullable = false)
    @NotNull
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "groupMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

    @Column(name = "GROUP_MEMBER_NAME", nullable = false)
    @NotNull
    private String groupMemberName;

    @Column(name = "GROUP_MEMBER_BIRTH")
    private LocalDate groupMemberBirth;

    @Column(name = "GROUP_MEMBER_PHONE")
    private String groupMemberPhone;

    @Column(name = "MESSAGE_CHECK", nullable = false)
    @NotNull
    private Boolean messageCheck;

}
