package com.kit.pillgood.controller;

import com.kit.pillgood.exeptions.exeption.AlreadyExistUserException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationFirebaseException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.AlreadyExistException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.LoginDTO;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 로그인 기능
     * @param: userDTO
     * @return: userDTO
     **/
    @PostMapping("/")
    public UserDTO login(@ModelAttribute @Validated(ValidationGroups.groupSearch.class) LoginDTO loginDTO) throws NonRegistrationFirebaseException, NonRegistrationUserException, EtcFirebaseException, AlreadyExistUserException {
            try{
                LOGGER.info("start login controller: ");
                return loginService.login(loginDTO);
            }catch (Exception e){
                LOGGER.error("login controller error : {}", e.getMessage());
                throw e;
            }
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
