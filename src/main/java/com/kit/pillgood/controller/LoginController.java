package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.AlreadyExistUserException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationFirebaseException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.LoginDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
public class LoginController {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 사용자의 로그인
     * @param: LoginDTO loginDTO, 로그인할 사용자 정보
     * @return: ResponseEntity<ResponseFormat>, 로그인 결과가 담긴 응답 객체
     **/
    @PostMapping("")
    public ResponseEntity<ResponseFormat> login(@ModelAttribute @Validated(ValidationGroups.groupSearch.class) LoginDTO loginDTO) throws NonRegistrationFirebaseException, NonRegistrationUserException, EtcFirebaseException, AlreadyExistUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), loginService.login(loginDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
