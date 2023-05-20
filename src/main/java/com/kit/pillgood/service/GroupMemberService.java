package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.AlreadyExistGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;
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
    public GroupMemberAndUserIndexDTO createGroupMember(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, AlreadyExistGroupException {
        Long userIndex = groupMemberAndUserIndexDTO.getUserIndex();
        String groupMemberPhonNumber = groupMemberAndUserIndexDTO.getGroupMemberPhone();

        if(userRepository.findByUserIndex(userIndex) == null){
            throw new NonRegistrationUserException();
        }

        if(groupMemberRepository.existsByUser_UserIndexAndGroupMemberPhone(userIndex, groupMemberPhonNumber)){
            throw new AlreadyExistGroupException();
        }

        User user = new User();
        user.setUserIndex(groupMemberAndUserIndexDTO.getUserIndex());

        GroupMember groupMember = GroupMember.builder()
                .groupMemberIndex(null)
                .user(user)
                .groupMemberName(groupMemberAndUserIndexDTO.getGroupMemberName())
                .groupMemberBirth(groupMemberAndUserIndexDTO.getGroupMemberBirth())
                .groupMemberPhone(groupMemberAndUserIndexDTO.getGroupMemberPhone())
                .messageCheck(groupMemberAndUserIndexDTO.getMessageCheck())
                .build();

        groupMember = groupMemberRepository.save(groupMember);

        groupMemberAndUserIndexDTO = EntityConverter.toGroupMemberAndUserIndexDTO(groupMember);

        return groupMemberAndUserIndexDTO;
    }

    /**
     * 그룹원을 수정하는 메소드
     * @param: 수정할 groupMemberIndex, 수정할 정보가 담긴 GroupMemberDTO
     * @return: DB에 저장된 그룹원 리턴
    **/
    @Transactional
    public GroupMemberAndUserIndexDTO updateGroupMember(Long groupMemberIndex, GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, NonRegistrationGroupException, AlreadyExistGroupException {

        GroupMember groupMember = groupMemberRepository.findByGroupMemberIndex(groupMemberIndex);

        if(groupMember != null) {
            GroupMemberAndUserIndexDTO newGroupMemberAndUserIndexDTO = settingUpdateGruopMemberData(groupMemberAndUserIndexDTO, groupMember);

            deleteGroupMember(groupMemberIndex);

           return createGroupMember(newGroupMemberAndUserIndexDTO);
        } else {
            throw new NonRegistrationGroupException();
        }
    }


    /**
     * updateGroupMember() 파라미터 groupMemberAndUserIndexDTO에 없는 값을 기존 그룹원의 값으로 채우는 함수
     * @param: 수정할 정보가 담긴 GroupMemberAndUserIndexDTO, 수정 전 GroupMember
     * @return: DB에 저장될 GroupMemberAndUserIndexDTO 리턴
     **/
    private GroupMemberAndUserIndexDTO settingUpdateGruopMemberData(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO, GroupMember groupMember){
        GroupMemberAndUserIndexDTO newGroupMemberAndUserIndexDTO = groupMemberAndUserIndexDTO;

        if(newGroupMemberAndUserIndexDTO.getGroupMemberBirth() == null){
            newGroupMemberAndUserIndexDTO.setGroupMemberBirth(groupMember.getGroupMemberBirth());
        }
        if(newGroupMemberAndUserIndexDTO.getGroupMemberName() == null){
            newGroupMemberAndUserIndexDTO.setGroupMemberName(groupMember.getGroupMemberName());
        }
        if(newGroupMemberAndUserIndexDTO.getGroupMemberPhone() == null){
            newGroupMemberAndUserIndexDTO.setGroupMemberPhone(groupMember.getGroupMemberPhone());
        }
        if(newGroupMemberAndUserIndexDTO.getMessageCheck() == null){
            newGroupMemberAndUserIndexDTO.setMessageCheck(groupMember.getMessageCheck());
        }
        if (newGroupMemberAndUserIndexDTO.getUserIndex() == null){
            newGroupMemberAndUserIndexDTO.setUserIndex(groupMember.getUser().getUserIndex());
        }

        return newGroupMemberAndUserIndexDTO;
    }

    /**
    * 그룹원 한명을 찾는 메소드
    * @param: 찾을 groupMemberIndex
    * @return: DB에서 찾은 그룹원 리턴
    **/
    @Transactional
    public GroupMemberDTO searchOneGroupMember(Long groupMemberIndex) throws NonRegistrationGroupException {
        GroupMember groupMember = groupMemberRepository.findByGroupMemberIndex(groupMemberIndex);

        if(groupMember == null){
            throw new NonRegistrationGroupException();
        }

        return EntityConverter.toGroupMemberDTO(groupMember);
    }

    /**
     * 모든 그룹원을 찾는 메소드
     * @param: 찾을 userIndex
     * @return: DB에서 찾은 모든 그룹원 리턴
    **/
    @Transactional
    public List<GroupMemberDTO> searchGroupMembersByUserIndex(Long userIndex) throws NonRegistrationUserException {
        if(userRepository.findByUserIndex(userIndex) == null){
            throw new NonRegistrationUserException();
        }

        List<GroupMember> groupMembers = groupMemberRepository.findByUser_UserIndex(userIndex);


        List<GroupMemberDTO> groupMemberDTOs = new ArrayList<>();

        for(GroupMember groupMember : groupMembers) {
            GroupMemberDTO groupMemberDTO = EntityConverter.toGroupMemberDTO(groupMember);
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
    public void deleteGroupMember(Long groupMemberIndex) throws NonRegistrationGroupException {
        if(groupMemberRepository.findByGroupMemberIndex(groupMemberIndex) == null){
            throw new NonRegistrationGroupException();
        }
        groupMemberRepository.deleteByGroupMemberIndex(groupMemberIndex);
    }
}
