package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.service.LoginService;
import com.kit.pillgood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    private final LoginService loginService;

    @Autowired
    public LoginController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    /**
     * 로그인 기능
     * @param: userDTO
     * @return: userDTO
    **/
    @PostMapping("/update_token")
    public UserDTO login(@ModelAttribute @Validated UserDTO userDTO) {
        UserDTO uDTO = userService.updateUserToken(userDTO);
        return uDTO;
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
