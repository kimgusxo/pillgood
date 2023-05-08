package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import com.kit.pillgood.persistence.projection.GroupMemberSummary;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberService {

    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Autowired
    public GroupMemberService(UserRepository userRepository, GroupMemberRepository groupMemberRepository) {
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
    }

    /**
     * 그룹원을 생성하는 메소드
     * @param: 생성할 정보가 담긴 GroupMemberDTO
     * @return: DB에 저장된 그룹원 리턴
    **/
    @Transactional
    public GroupMemberAndUserIndexDTO createGroupMember(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) {
        User user = new User();
        user.setUserIndex(groupMemberAndUserIndexDTO.getUserIndex());

        GroupMember groupMember = GroupMember.builder()
                .groupMemberIndex(groupMemberAndUserIndexDTO.getGroupMemberIndex())
                .user(user)
                .groupMemberName(groupMemberAndUserIndexDTO.getGroupMemberName())
                .groupMemberBirth(groupMemberAndUserIndexDTO.getGroupMemberBirth())
                .groupMemberPhone(groupMemberAndUserIndexDTO.getGroupMemberPhone())
                .messageCheck(groupMemberAndUserIndexDTO.getMessageCheck())
                .build();

        groupMember = groupMemberRepository.save(groupMember);

        groupMemberAndUserIndexDTO = groupMemberAndUserIndexDTO.builder()
                .groupMemberIndex(groupMember.getGroupMemberIndex())
                .userIndex(groupMember.getUser().getUserIndex())
                .groupMemberName(groupMember.getGroupMemberName())
                .groupMemberBirth(groupMember.getGroupMemberBirth())
                .groupMemberPhone(groupMember.getGroupMemberPhone())
                .messageCheck(groupMember.getMessageCheck())
                .build();

        return groupMemberAndUserIndexDTO;
    }

    /**
     * 그룹원을 수정하는 메소드
     * @param: 수정할 groupMemberIndex, 수정할 정보가 담긴 GroupMemberDTO
     * @return: DB에 저장된 그룹원 리턴
    **/
    @Transactional
    public GroupMemberAndUserIndexDTO updateGroupMember(Long groupMemberIndex, GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) {
        GroupMemberSummary groupMemberSummary = groupMemberRepository.findByGroupMemberIndex(groupMemberIndex);

        if(groupMemberSummary != null) {
           return createGroupMember(groupMemberAndUserIndexDTO);
        } else {
            return null;
        }
    }

    /**
    * 그룹원 한명을 찾는 메소드
    * @param: 찾을 groupMemberIndex
    * @return: DB에서 찾은 그룹원 리턴
    **/
    @Transactional
    public GroupMemberDTO searchOneGroupMember(Long groupMemberIndex) {
        GroupMemberSummary groupMemberSummary = groupMemberRepository.findByGroupMemberIndex(groupMemberIndex);
        GroupMemberDTO groupMemberDTO = EntityConverter.toGroupMemberDTOFromSummary(groupMemberSummary);

        return groupMemberDTO;
    }

    /**
     * 모든 그룹원을 찾는 메소드
     * @param: 찾을 userIndex
     * @return: DB에서 찾은 모든 그룹원 리턴
    **/
    @Transactional
    public List<GroupMemberDTO> searchGroupMembersByUserIndex(Long userIndex) {
        List<GroupMemberSummary> groupMembers = groupMemberRepository.findByUser_UserIndex(userIndex);

        List<GroupMemberDTO> groupMemberDTOs = new ArrayList<>();

        for(GroupMemberSummary groupMember : groupMembers) {
            GroupMemberDTO groupMemberDTO = EntityConverter.toGroupMemberDTOFromSummary(groupMember);
            groupMemberDTOs.add(groupMemberDTO);
        }

        return groupMemberDTOs;
    }

    /**
     * 그룹원을 삭제하는 메소드
     * @param: 삭제할 groupMemberIndex
     * @return: 리턴 없음
    **/
    @Transactional
    public void deleteGroupMember(Long groupMemberIndex) {
        groupMemberRepository.deleteByGroupMemberIndex(groupMemberIndex);
    }
}
