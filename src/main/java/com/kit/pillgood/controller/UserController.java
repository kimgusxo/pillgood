package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
        log.info("deleteUser - 요청 수신, userIndex={}", userIndex);

        boolean result = userService.deleteFirebaseUser(userIndex);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), result);

        log.debug("deleteUser - 삭제 완료, userIndex={}, result={}", userIndex, result);

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

        log.info("updateUserToken - 요청 수신, userIndex={}, tokenLength={}",
                userIndex,
                userDTO.getUserFcmToken() != null ? userDTO.getUserFcmToken().length() : 0);

        ResponseFormat responseFormat = ResponseFormat.of(
                "success",
                HttpStatus.OK.value(),
                userService.updateUserToken(userIndex, userDTO)
        );

        log.debug("updateUserToken - 토큰 갱신 완료, userIndex={}", userIndex);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
