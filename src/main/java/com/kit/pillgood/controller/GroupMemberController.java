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
     * 그룹원 생성
     * @param: GroupMemberAndUserIndexDTO
     * @return: GroupMemberAndUserIndexDTO
    **/
    @PostMapping("/create")
    public ResponseEntity<ResponseFormat> createGroupMember(@ModelAttribute @Validated(ValidationGroups.groupCreate.class) GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, AlreadyExistGroupException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), groupMemberService.createGroupMember(groupMemberAndUserIndexDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    @GetMapping("/search/{group-member-index}")
    public ResponseEntity<ResponseFormat> getGroupMemberByGroupMemberIndex(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO = groupMemberService.searchOneGroupMember(groupMemberIndex);

        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), groupMemberAndUserIndexDTO);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    @GetMapping("/search/group-members")
    public ResponseEntity<ResponseFormat> getGroupMembersByUserIndex(@RequestParam Long userIndex) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), groupMemberService.searchGroupMembersByUserIndex(userIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    @PutMapping("/update/{group-member-index}")
    public ResponseEntity<ResponseFormat> updateGroupMember(@PathVariable (name="group-member-index") Long groupMemberIndex,
                                                           @ModelAttribute @Validated(ValidationGroups.groupUpdate.class) GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, NonRegistrationGroupException, AlreadyExistGroupException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(),  groupMemberService.updateGroupMember(groupMemberIndex, groupMemberAndUserIndexDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{group-member-index}")
    public ResponseEntity<ResponseFormat> deleteGroupMember(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        groupMemberService.deleteGroupMember(groupMemberIndex);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
