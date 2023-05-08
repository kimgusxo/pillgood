package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import com.kit.pillgood.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public GroupMemberDTO createGroupMember(@Valid @ModelAttribute GroupMemberDTO groupMemberDTO) {
        return groupMemberService.createGroupMember(groupMemberDTO);
    }

    @GetMapping("/search/{group-member-index}")
    public GroupMemberDTO getGroupMemberByGroupMemberIndex(@PathVariable(name="group-member-index") Long groupMemberIndex) {
        return groupMemberService.searchOneGroupMember(groupMemberIndex);
    }

    @GetMapping("/search/group-members")
    public List<GroupMemberDTO> getGroupMembersByUserIndex(@RequestParam Long userIndex) {
        return groupMemberService.searchGroupMembersByUserIndex(userIndex);
    }

    @PutMapping("/update/{group-member-index}")
    public GroupMemberDTO updateGroupMember(@Valid @PathVariable(name="group-member-index") Long groupMemberIndex,
                                                           @ModelAttribute GroupMemberDTO groupMemberDTO) {
        return groupMemberService.updateGroupMember(groupMemberIndex, groupMemberDTO);
    }

    @DeleteMapping("/delete/{group-member-index}")
    public void deleteGroupMember(@Valid @PathVariable(name="group-member-index") Long groupMemberIndex) {
        groupMemberService.deleteGroupMember(groupMemberIndex);
    }

}
