package com.kit.pillgood.controller;

import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.AlreadyExistGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/group-member")
public class GroupMemberController {
    private final GroupMemberService groupMemberService;

    @Autowired
    public GroupMemberController(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }
    
    @PostMapping("/create")
    public GroupMemberAndUserIndexDTO createGroupMember(@ModelAttribute @Validated(ValidationGroups.groupCreate.class) GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, AlreadyExistGroupException {
        return groupMemberService.createGroupMember(groupMemberAndUserIndexDTO);
    }

    @GetMapping("/search/{group-member-index}")
    public GroupMemberDTO getGroupMemberByGroupMemberIndex(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        return groupMemberService.searchOneGroupMember(groupMemberIndex);
    }

    @GetMapping("/search/group-members")
    public List<GroupMemberDTO> getGroupMembersByUserIndex(@RequestParam Long userIndex) throws NonRegistrationUserException {
        return groupMemberService.searchGroupMembersByUserIndex(userIndex);
    }

    @PutMapping("/update/{group-member-index}")
    public GroupMemberAndUserIndexDTO updateGroupMember(@PathVariable (name="group-member-index") Long groupMemberIndex,
                                                           @ModelAttribute @Validated(ValidationGroups.groupUpdate.class) GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO) throws NonRegistrationUserException, NonRegistrationGroupException, AlreadyExistGroupException {
        return groupMemberService.updateGroupMember(groupMemberIndex, groupMemberAndUserIndexDTO);
    }

    @DeleteMapping("/delete/{group-member-index}")
    public void deleteGroupMember(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        groupMemberService.deleteGroupMember(groupMemberIndex);
    }

}
