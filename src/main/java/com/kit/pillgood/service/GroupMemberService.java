package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.AlreadyExistGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.TransactionFailedException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public GroupMemberAndUserIndexDTO createGroupMember(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, AlreadyExistGroupException {

        try{
            Long userIndex = groupMemberAndUserIndexDTO.getUserIndex();
            String groupMemberPhoneNumber = groupMemberAndUserIndexDTO.getGroupMemberPhone();

            if(!userRepository.existsByUserIndex(userIndex)){
                LOGGER.info(".createGroupMember [err] 존재하지 않은 userIndex={} 검색", userIndex);
                throw new NonRegistrationUserException();
            }

            if(groupMemberRepository.existsByGroupMemberPhone(groupMemberPhoneNumber)){
                LOGGER.info(".createGroupMember [err] 이미 등록된 전화번호={} 등록 시도", groupMemberPhoneNumber);
                throw new AlreadyExistGroupException();
            }

            User user = new User();
            user.setUserIndex(groupMemberAndUserIndexDTO.getUserIndex());

            GroupMember groupMember = EntityConverter.toGroupMember(groupMemberAndUserIndexDTO);

            groupMember = groupMemberRepository.save(groupMember);

            groupMemberAndUserIndexDTO = EntityConverter.toGroupMemberAndUserIndexDTO(groupMember);
            LOGGER.info(".createGroupMember 그룹원 생성 완료{}", groupMemberAndUserIndexDTO);

            return groupMemberAndUserIndexDTO;
        }
        catch (NonRegistrationUserException e){
            throw new NonRegistrationUserException();
        }
        catch (AlreadyExistGroupException e) {
            throw new AlreadyExistGroupException();
        }

    }

    @Transactional
    public GroupMemberAndUserIndexDTO updateGroupMember(Long groupMemberIndex, GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, NonRegistrationGroupException, AlreadyExistGroupException {
        try{
            if(!userRepository.existsByUserIndex(groupMemberAndUserIndexDTO.getUserIndex())){
                LOGGER.info(".updateGroupMember [err] 존재하지 않는 UserIndex={} 조회", groupMemberAndUserIndexDTO.getUserIndex());
                throw new NonRegistrationUserException();
            }

            GroupMember groupMember = groupMemberRepository.findByGroupMemberIndex(groupMemberIndex);

            if(groupMember == null) {
                LOGGER.info(".updateGroupMember [err] 존재하지 않는 그룹맴버={} 조회", groupMemberAndUserIndexDTO);
                throw new NonRegistrationGroupException();
            }

            groupMember = settingUpdateGroupMemberData(groupMemberAndUserIndexDTO, groupMember);

            groupMember = groupMemberRepository.save(groupMember);

            GroupMemberAndUserIndexDTO newGroupMemberAndUserIndexDTO = EntityConverter.toGroupMemberAndUserIndexDTO(groupMember);

            LOGGER.info(".updateGroupMember 그룹맴버 수정 완료 {}", newGroupMemberAndUserIndexDTO);

            return newGroupMemberAndUserIndexDTO;
        }
        catch (NonRegistrationUserException ignore){
            throw new NonRegistrationUserException();
        }
        catch (NonRegistrationGroupException ignore){
            throw new NonRegistrationGroupException();
        }

    }

    private GroupMember settingUpdateGroupMemberData(GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO, GroupMember groupMember){

        if(groupMemberAndUserIndexDTO.getGroupMemberBirth() != null){
            groupMember.setGroupMemberBirth(groupMemberAndUserIndexDTO.getGroupMemberBirth());
        }
        if(groupMemberAndUserIndexDTO.getGroupMemberName() != null){
            groupMember.setGroupMemberName(groupMemberAndUserIndexDTO.getGroupMemberName());
        }
        if(groupMemberAndUserIndexDTO.getGroupMemberPhone() != null){
            groupMember.setGroupMemberPhone(groupMemberAndUserIndexDTO.getGroupMemberPhone());
        }
        if(groupMemberAndUserIndexDTO.getMessageCheck() != null){
            groupMember.setMessageCheck(groupMemberAndUserIndexDTO.getMessageCheck());
        }
        if (groupMemberAndUserIndexDTO.getUserIndex() != null){
            User user = User.builder()
                    .userIndex(groupMemberAndUserIndexDTO.getUserIndex()).build();
            groupMember.setUser(user);
        }

        return groupMember;
    }

    @Transactional
    public GroupMemberAndUserIndexDTO searchOneGroupMember(Long groupMemberIndex) throws NonRegistrationGroupException {
        try{
            GroupMember groupMember = groupMemberRepository.findByGroupMemberIndex(groupMemberIndex);

            if(groupMember == null){
                LOGGER.info("[err] 존재하지 않는 groupMemberIndex={} 조회", groupMemberIndex);
                throw new NonRegistrationGroupException();
            }

            return EntityConverter.toGroupMemberAndUserIndexDTO(groupMember);
        }
        catch (NonRegistrationGroupException ignore){
            throw new NonRegistrationGroupException();
        }

    }

    @Transactional
    public List<GroupMemberAndUserIndexDTO> searchGroupMembersByUserIndex(Long userIndex) throws NonRegistrationUserException {
        try{
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
        catch (NonRegistrationUserException ignore){
            throw new NonRegistrationUserException();
        }

    }

    @Transactional
    public void deleteGroupMember(Long groupMemberIndex) throws NonRegistrationGroupException {
        try{
            if(!groupMemberRepository.existsByGroupMemberIndex(groupMemberIndex)){
                LOGGER.info("[err] 존재하지 않는 groupMemberIndex={} 조회", groupMemberIndex);
                throw new NonRegistrationGroupException();
            }
            groupMemberRepository.deleteByGroupMemberIndex(groupMemberIndex);
            LOGGER.info("그룹원 삭제 완료 groupMemberIndex={}", groupMemberIndex);
        }
        catch (NonRegistrationGroupException ignore){
            throw new NonRegistrationGroupException();
        }

    }
}
