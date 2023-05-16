package com.kit.pillgood.service;

import com.kit.pillgood.persistence.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인 기능
     * @param: 생성 될 userDTO
     * @return: 생성 된 userDTO
     **/
    public UserDTO login(String userEmail, String userToken){

        // firebase에 등록 여부 확인
        if(!userService.isFirebaseUser(userEmail)){
            System.out.println("firebase 미등록");
            return null; // firebase에 등록되지 않은 유저 예외
        }

        // mysql에 등록되지 않은 유저
        UserDTO userDTO = userService.searchUser(userEmail);
        if(userDTO == null){
            // mysql 생성
            userDTO = UserDTO.builder()
                            .userIndex(null)
                            .userFcmToken(userToken)
                            .userEmail(userEmail)
                            .build();

            userService.createUser(userDTO);
        }

        return userDTO;

    }
}
