package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public UserDTO createUser(@Valid @ModelAttribute UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

   @DeleteMapping("/delete/{user-index}")
   public void deleteUser(@Valid @PathVariable(name="user-index") Long userIndex) {
        userService.deleteUser(userIndex);
   }
}
