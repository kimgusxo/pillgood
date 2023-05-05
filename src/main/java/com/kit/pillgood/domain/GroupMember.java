package com.kit.pillgood.domain;

import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_MEMBER_INDEX")
    private Long groupMemberIndex;

    @ManyToOne
    @JoinColumn(name="USER_INDEX")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "groupMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prescription> prescriptions = new ArrayList<>();

    @Column(name = "GROUP_MEMBER_NAME")
    @NonNull
    private String groupMemberName;

    @Column(name = "GROUP_MEMBER_BIRTH")
    private LocalDate groupMemberBirth;

    @Column(name = "GROUP_MEMBER_PHONE")
    private String groupMemberPhone;

    @Column(name = "MESSAGE_CHECK")
    @NonNull
    private Boolean messageCheck;

}
