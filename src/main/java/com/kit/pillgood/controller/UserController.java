package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/delete/{user-index}")
    public boolean deleteUser(@PathVariable(name="user-index") Long userIndex) throws EtcFirebaseException, NonRegistrationUserException {
        return userService.deleteFirebaseUser(userIndex);
   }

    /**
     * 토큰 갱신 기능
     * @param: userDTO
     * @return: userDTO
     **/
    @PutMapping("/update-token/{user-index}")
    public ResponseEntity<ResponseFormat> updateUserToken(@PathVariable("user-index") Long userIndex,
            @ModelAttribute @Validated(ValidationGroups.groupUpdate.class) UserDTO userDTO) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), userService.updateUserToken(userIndex, userDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
