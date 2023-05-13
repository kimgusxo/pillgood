package com.kit.pillgood.service;

import com.kit.pillgood.persistence.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }


    public UserDTO login(UserDTO uDTO){

        // firebase에 등록되지 않음 유저
        String userEmail = uDTO.getUserEmail();
        if(!userService.isFirebaseUser(userEmail)){
            return null; // firebase에 등록되지 않은 유저 예외
        }

        // mysql에 등록되지 않은 유저
        UserDTO userDTO = userService.searchUser(userEmail);
        if(userDTO == null){
            // mysql 생성
            userDTO = UserDTO.builder()
                            .userIndex(null)
                            .userFcmToken(null)
                            .userEmail(userEmail)
                            .build();

            userService.createUser(userDTO);
        }

        return userDTO;

    }
}
