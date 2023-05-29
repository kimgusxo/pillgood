package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.AlreadyExistGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberService {
    private final Logger LOGGER = LoggerFactory.getLogger(GroupMemberService.class);
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

        if(!userRepository.existsByUserIndex(userIndex)){
            LOGGER.info("[err] 존재하지 않은 userIndex={} 검색", userIndex);
            throw new NonRegistrationUserException();
        }

        if(groupMemberRepository.existsByGroupMemberPhone(groupMemberPhonNumber)){
            LOGGER.info("[err] 이미 등록된 전화번호={} 등록 시도", groupMemberPhonNumber);
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
        LOGGER.info("그룹원 생성 완료{}", groupMemberAndUserIndexDTO);

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

        if(groupMember == null) {
            LOGGER.info(".updateGroupMember [err] 존재하지 않는 그룹맴버={} 조회", groupMemberAndUserIndexDTO);
            throw new NonRegistrationGroupException();
        }

        GroupMemberAndUserIndexDTO newGroupMemberAndUserIndexDTO = settingUpdateGroupMemberData(groupMemberAndUserIndexDTO, groupMember);
        GroupMemberAndUserIndexDTO newGroupMemberAndUserDTO = createGroupMember(newGroupMemberAndUserIndexDTO);
        deleteGroupMember(groupMemberIndex);

        LOGGER.info(".updateGroupMember 그룹맴버 수정 완료 {}", newGroupMemberAndUserDTO);

        return newGroupMemberAndUserDTO;
    }


    /**
     * updateGroupMember() 파라미터 groupMemberAndUserIndexDTO에 없는 값을 기존 그룹원의 값으로 채우는 함수
     * @param: 수정할 정보가 담긴 GroupMemberAndUserIndexDTO, 수정 전 GroupMember
     * @return: DB에 저장될 GroupMemberAndUserIndexDTO 리턴
     **/
    private GroupMemberAndUserIndexDTO settingUpdateGroupMemberData(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO, GroupMember groupMember){

        if(groupMemberAndUserIndexDTO.getGroupMemberBirth() == null){
            groupMemberAndUserIndexDTO.setGroupMemberBirth(groupMember.getGroupMemberBirth());
        }
        if(groupMemberAndUserIndexDTO.getGroupMemberName() == null){
            groupMemberAndUserIndexDTO.setGroupMemberName(groupMember.getGroupMemberName());
        }
        if(groupMemberAndUserIndexDTO.getGroupMemberPhone() == null){
            groupMemberAndUserIndexDTO.setGroupMemberPhone(groupMember.getGroupMemberPhone());
        }
        if(groupMemberAndUserIndexDTO.getMessageCheck() == null){
            groupMemberAndUserIndexDTO.setMessageCheck(groupMember.getMessageCheck());
        }
        if (groupMemberAndUserIndexDTO.getUserIndex() == null){
            groupMemberAndUserIndexDTO.setUserIndex(groupMember.getUser().getUserIndex());
        }

        return groupMemberAndUserIndexDTO;
    }

    /**
    * 그룹원 한명을 찾는 메소드
    * @param: 찾을 groupMemberIndex
    * @return: DB에서 찾은 그룹원 리턴
    **/
    @Transactional
    public GroupMemberAndUserIndexDTO searchOneGroupMember(Long groupMemberIndex) throws NonRegistrationGroupException {
        GroupMember groupMember = groupMemberRepository.findByGroupMemberIndex(groupMemberIndex);

        if(groupMember == null){
            LOGGER.info("[err] 존재하지 않는 groupMemberIndex={} 조회", groupMemberIndex);
            throw new NonRegistrationGroupException();
        }

        return EntityConverter.toGroupMemberAndUserIndexDTO(groupMember);
    }

    /**
     * 모든 그룹원을 찾는 메소드
     * @param: 찾을 userIndex
     * @return: DB에서 찾은 모든 그룹원 리턴
    **/
    @Transactional
    public List<GroupMemberAndUserIndexDTO> searchGroupMembersByUserIndex(Long userIndex) throws NonRegistrationUserException {
        if(!userRepository.existsByUserIndex(userIndex)){
            LOGGER.info("[err] 존재하지 않는 userIndex={} 조회", userIndex);
            throw new NonRegistrationUserException();
        }

        List<GroupMember> groupMembers = groupMemberRepository.findByUser_UserIndex(userIndex);


        List<GroupMemberAndUserIndexDTO> groupMemberAndUserIndexDTOS = new ArrayList<>();

        for(GroupMember groupMember : groupMembers) {
            GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO = EntityConverter.toGroupMemberAndUserIndexDTO(groupMember);
            groupMemberAndUserIndexDTOS.add(groupMemberAndUserIndexDTO);
        }

        LOGGER.info("그룹원 조회 완료 {}", groupMemberAndUserIndexDTOS);

        return groupMemberAndUserIndexDTOS;
    }

    /**
     * 그룹원을 삭제하는 메소드
     * @param: 삭제할 groupMemberIndex
     * @return: void
    **/
    @Transactional
    public void deleteGroupMember(Long groupMemberIndex) throws NonRegistrationGroupException {
        if(!groupMemberRepository.existsByGroupMemberIndex(groupMemberIndex)){
            LOGGER.info("[err] 존재하지 않는 groupMemberIndex={} 조회", groupMemberIndex);
            throw new NonRegistrationGroupException();
        }
        groupMemberRepository.deleteByGroupMemberIndex(groupMemberIndex);
        LOGGER.info("그룹원 삭제 완료 groupMemberIndex={}", groupMemberIndex);
    }
}
