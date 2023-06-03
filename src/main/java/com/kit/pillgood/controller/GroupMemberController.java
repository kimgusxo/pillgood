package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.AlreadyExistGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-member")
public class GroupMemberController {
    private final GroupMemberService groupMemberService;

    @Autowired
    public GroupMemberController(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }

    /**
     * 사용자의 그룹원 생성
     * @param: GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO, 생성할 그룹원 정보
     * @return: ResponseEntity<ResponseFormat>, 생성된 그룹원 결과가 담긴 응답 객체
    **/
    @PostMapping("/create")
    public ResponseEntity<ResponseFormat> createGroupMember(@ModelAttribute @Validated(ValidationGroups.groupCreate.class) GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, AlreadyExistGroupException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), groupMemberService.createGroupMember(groupMemberAndUserIndexDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 사용자의 해당 그룹원 조회
     * @param: GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO, 조회할 그룹원 인덱스
     * @return: ResponseEntity<ResponseFormat>, 조회된 그룹원 결과가 담긴 응답 객체
     **/
    @GetMapping("/search/{group-member-index}")
    public ResponseEntity<ResponseFormat> getGroupMemberByGroupMemberIndex(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO = groupMemberService.searchOneGroupMember(groupMemberIndex);

        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), groupMemberAndUserIndexDTO);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 사용자의 해당 그룹원 리스트 조회
     * @param: Long userIndex, 조회할 사용자 인덱스
     * @return: ResponseEntity<ResponseFormat>, 조회된 그룹원 리스트 결과가 담긴 응답 객체
     **/
    @GetMapping("/search/group-members")
    public ResponseEntity<ResponseFormat> getGroupMembersByUserIndex(@RequestParam Long userIndex) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), groupMemberService.searchGroupMembersByUserIndex(userIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 사용자의 해당 그룹원 수정
     * @param: Long groupMemberIndex, 수정할 그룹원 인덱스
     * @Param: GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO, 수정할 그룹원 정보
     * @return: ResponseEntity<ResponseFormat>, 수정된 결과가 담긴 응답 객체
     **/
    @PutMapping("/update/{group-member-index}")
    public ResponseEntity<ResponseFormat> updateGroupMember(@PathVariable (name="group-member-index") Long groupMemberIndex,
                                                           @ModelAttribute @Validated(ValidationGroups.groupUpdate.class) GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, NonRegistrationGroupException, AlreadyExistGroupException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(),  groupMemberService.updateGroupMember(groupMemberIndex, groupMemberAndUserIndexDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 사용자의 해당 그룹원 삭제
     * @param: Long groupMemberIndex, 삭제할 그룹원 인덱스
     * @return: ResponseEntity<ResponseFormat>, 삭제된 결과가 담긴 응답 객체
     **/
    @DeleteMapping("/delete/{group-member-index}")
    public ResponseEntity<ResponseFormat> deleteGroupMember(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        groupMemberService.deleteGroupMember(groupMemberIndex);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
