package com.kit.pillgood.controller;

import com.kit.pillgood.exeptions.exeption.NonRegistrationFirebaseException;
import com.kit.pillgood.exeptions.exeption.superExeption.BindException;
import com.kit.pillgood.persistence.dto.LoginDTO;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 로그인 기능
     * @param: userDTO
     * @return: userDTO
     **/
    @PostMapping("/login")
    public UserDTO createUser(@ModelAttribute @Validated(ValidationGroups.groupSearch.class) LoginDTO loginDTO) throws NonRegistrationFirebaseException, BindException {
        return loginService.login(loginDTO);
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public void handleLoginFailure() {
        // 로그인 실패 작업
    }

}
