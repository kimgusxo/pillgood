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
@RequestMapping("/users")
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
    @DeleteMapping("/{userIndex}")
    public ResponseEntity<ResponseFormat> deleteUser(@PathVariable(name="userIndex") Long userIndex) throws EtcFirebaseException {
        boolean result = userService.deleteFirebaseUser(userIndex);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), result);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 사용자의 토큰 갱신
     * @param: Long userIndex, 토큰정보를 갱신할 사용자 인덱스
     * @param: UserDTO userDTO, 토큰정보를 갱신할 사용자 정보
     * @return: ResponseEntity<ResponseFormat>, 토큰 수정 결과가 담긴 응답 객체
     **/
    @PutMapping("/{userIndex}")
    public ResponseEntity<ResponseFormat> updateUserToken(@PathVariable("userIndex") Long userIndex,
            @RequestBody @Validated(ValidationGroups.groupUpdate.class) UserDTO userDTO) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), userService.updateUserToken(userIndex, userDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
