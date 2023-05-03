package com.kit.pillgood.service;

import com.kit.pillgood.domain.User;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저를 생성하는 메소드
     * @param: 생성할 UserDTO
     * @return: DB에서 생성할 UserDTO
    **/
//    public UserDTO createUser(UserDTO userDTO) {
//        User user = EntityConverter.toUser(userDTO);
//        userDTO = EntityConverter.toUserDTO(userRepository.save(user));
//        return userDTO;
//    }

    public void deleteUser(Long userIndex) {
        userRepository.deleteById(userIndex);
    }

}
