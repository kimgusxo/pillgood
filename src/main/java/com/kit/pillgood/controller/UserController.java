package com.kit.pillgood.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.AlreadyExistUserException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/delete/{user-index}")
    public boolean deleteUser(@PathVariable(name="user-index") Long userIndex) throws EtcFirebaseException, NonRegistrationUserException {
        return userService.deleteFirebaseUser(userIndex);
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
    @PutMapping("/update-token/{user-index}")
    public ResponseEntity<ResponseFormat> updateUserToken(@PathVariable("user-index") Long userIndex,
            @ModelAttribute @Validated(ValidationGroups.groupUpdate.class) UserDTO userDTO) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("성공 코드", HttpStatus.OK.value(), userService.updateUserToken(userIndex, userDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
