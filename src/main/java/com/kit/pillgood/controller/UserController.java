package com.kit.pillgood.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("")
//    public UserDTO createUser(@ModelAttribute @Validated UserDTO userDTO) {
//        return userService.createUser(userDTO);
//    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestParam Long userIndex) {
        return userService.deleteUser(userIndex);
   }

//    @GetMapping("/users")
//    public List<String> getFirebaseUsers() {
//        List<String> list = null;
//        try {
//            list = userService.getFirebaseUsers();
//        } catch (FirebaseAuthException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
//    }
//
//    @PostMapping("/authUser")
//    public boolean isFirebaseUsers(@RequestParam String email) {
//        boolean isUser = userService.isFirebaseUser(email);
//
//        return isUser;
//    }
//
//    @PostMapping("/delete")
//    public boolean deleteFirebaseUser(@RequestParam String email){
//        boolean deleteResponse = userService.deleteFirevaseUser(email);
//        return deleteResponse;
//    }

    /**
     * 토큰 갱신 기능
     * @param: userDTO
     * @return: userDTO
     **/
    @PostMapping("/update_token")
    public UserDTO login(@ModelAttribute @Validated UserDTO userDTO) {
        UserDTO uDTO = userService.updateUserToken(userDTO);
        return uDTO;
    }

}
