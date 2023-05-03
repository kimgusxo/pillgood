package com.kit.pillgood.util;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;

public class EntityConverter {
    public static GroupMember toGroupMember(GroupMemberDTO groupMemberDTO) {
        GroupMember groupMember = GroupMember.builder()
                .groupMemberIndex(groupMemberDTO.getGroupMemberIndex())
                .user(groupMemberDTO.getUser())
                .prescriptions(groupMemberDTO.getPrescriptions())
                .groupMemberName(groupMemberDTO.getGroupMemberName())
                .groupMemberBirth(groupMemberDTO.getGroupMemberBirth())
                .groupMemberPhone(groupMemberDTO.getGroupMemberPhone())
                .messageCheck(groupMemberDTO.getMessageCheck())
                .build();
        return groupMember;
    }

    public static GroupMemberDTO toGroupMemberDTO(GroupMember groupMember) {
        GroupMemberDTO groupMemberDTO = GroupMemberDTO.builder()
                .groupMemberIndex(groupMember.getGroupMemberIndex())
                .user(groupMember.getUser())
                .prescriptions(groupMember.getPrescriptions())
                .groupMemberName(groupMember.getGroupMemberName())
                .groupMemberBirth(groupMember.getGroupMemberBirth())
                .groupMemberPhone(groupMember.getGroupMemberPhone())
                .messageCheck(groupMember.getMessageCheck())
                .build();
        return groupMemberDTO;
    }
}
