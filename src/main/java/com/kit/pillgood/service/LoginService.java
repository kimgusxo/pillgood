package com.kit.pillgood.service;

import com.kit.pillgood.exeptions.exeption.NonRegistrationFirebaseException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.LoginDTO;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private final UserService userService;
    private final UserRepository userRepository;

    public LoginService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO login(LoginDTO loginDTO) throws NonRegistrationFirebaseException, EtcFirebaseException {
        String userEmail = loginDTO.getUserEmail();
        String userToken = loginDTO.getUserToken();
        try {
            // firebase에 등록 여부 확인
            if (!userService.isFirebaseUser(userEmail)) {
                LOGGER.info(".login [err] 존재하지 않는 FirebaseUser={} 조회", userEmail);
                throw new NonRegistrationFirebaseException();
            }

            // mysql에 등록되지 않은 유저
            if(userRepository.existsByUserEmail(loginDTO.getUserEmail())){
                throw new NonRegistrationUserException();
            }

            // token이 변경된 유저
            UserDTO userDTO = userService.searchUser(userEmail);
            if (!userDTO.getUserFcmToken().equals(userToken)) {
                // mysql 생성
                userDTO = UserDTO.builder()
                        .userIndex(userDTO.getUserIndex())
                        .userFcmToken(userToken)
                        .userEmail(userEmail)
                        .build();
                userDTO = userService.updateUserToken(userDTO.getUserIndex(), userDTO);
            }
            LOGGER.info(".login 사용자 로그인 {}", userEmail);

            return userDTO;
        } catch (NonRegistrationFirebaseException ignore) {
            throw new NonRegistrationFirebaseException();
        } catch (NonRegistrationUserException ignore) {
            LOGGER.info(".login [err] 등록되지 않은 유저 검색");

            // mysql 생성
            UserDTO userDTO = UserDTO.builder()
                    .userIndex(null)
                    .userFcmToken(userToken)
                    .userEmail(userEmail)
                    .build();
            userDTO = userService.createUser(userDTO);

            LOGGER.info(".login 사용자 로그인 {}", userEmail);

            return userDTO;
        } catch (EtcFirebaseException ignore) {
            throw new EtcFirebaseException();
        }
    }
}
