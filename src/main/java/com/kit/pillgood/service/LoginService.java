package com.kit.pillgood.service;

import com.kit.pillgood.exeptions.exeption.AlreadyExistUserException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationFirebaseException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.TransactionFailedException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.LoginDTO;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.transaction.TransactionalException;

@Service
public class LoginService {
    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인 기능
     * @param: 생성 될 userDTO
     * @return: 생성 된 userDTO
     **/
    @Transactional
    public UserDTO login(LoginDTO loginDTO) throws NonRegistrationFirebaseException, NonRegistrationUserException, EtcFirebaseException {
        try {
            String userEmail = loginDTO.getUserEmail();
            String userToken = loginDTO.getUserToken();

            // firebase에 등록 여부 확인
            if (!userService.isFirebaseUser(userEmail)) {
                LOGGER.info("[err] 존재하지 않는 FirebaseUser={} 조회", userEmail);
                throw new NonRegistrationFirebaseException();
            }

            // mysql에 등록되지 않은 유저
            UserDTO userDTO = userService.searchUser(userEmail);
            if (userDTO == null) {
                // mysql 생성
                userDTO = UserDTO.builder()
                        .userIndex(null)
                        .userFcmToken(userToken)
                        .userEmail(userEmail)
                        .build();

                userService.createUser(userDTO);
            }
            LOGGER.info("사용자 로그인 {}", userEmail);
            return userDTO;
        } catch (NonRegistrationFirebaseException ignore) {
            throw new NonRegistrationFirebaseException();
        } catch (NonRegistrationUserException ignore) {
            throw new NonRegistrationUserException();
        } catch (EtcFirebaseException ignore) {
            throw new EtcFirebaseException();
        } catch (Exception ignore) {
            throw new TransactionFailedException();
        }
    }
}
