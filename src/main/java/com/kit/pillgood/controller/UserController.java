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

    /**
     * 해당 사용자 삭제
     * @param: Long userIndex, 삭제할 사용자 인덱스
     * @return: ResponseEntity<ResponseFormat>, 사용자 삭제 결과가 담긴 응답 객체
     **/
    @DeleteMapping("/delete/{user-index}")
    public boolean deleteUser(@PathVariable(name="user-index") Long userIndex) throws EtcFirebaseException, NonRegistrationUserException {
        return userService.deleteFirebaseUser(userIndex);
   }

    /**
     * 사용자의 토큰 갱신
     * @param: Long userIndex, 토큰정보를 갱신할 사용자 인덱스
     * @param: UserDTO userDTO, 토큰정보를 갱신할 사용자 정보
     * @return: ResponseEntity<ResponseFormat>, 토큰 수정 결과가 담긴 응답 객체
     **/
    @PutMapping("/update-token/{user-index}")
    public ResponseEntity<ResponseFormat> updateUserToken(@PathVariable("user-index") Long userIndex,
            @ModelAttribute @Validated(ValidationGroups.groupUpdate.class) UserDTO userDTO) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), userService.updateUserToken(userIndex, userDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
