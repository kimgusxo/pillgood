package com.kit.pillgood.service;

import com.google.firebase.auth.*;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public UserDTO createUser(UserDTO userDTO) {
        User user = EntityConverter.toUser(userDTO);
        userDTO = EntityConverter.toUserDTO(userRepository.save(user));
        return userDTO;
    }

    public void deleteUser(Long userIndex) {
        userRepository.deleteById(userIndex);
    }

    /**
     * Firebase의 전체 유저의 이메일을 가져오는 메소드
     * @param: null
     * @return: 전체 유저 이메일 리스트
     **/
    public List<String> getFirebaseUsers() throws FirebaseAuthException {

        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        List<String> uidList = new ArrayList<>();

        while (page != null) {
            for (ExportedUserRecord user : page.getValues()) {
                System.out.println("User: " + user.getEmail());
            }
            page = page.getNextPage();
        }

        page = FirebaseAuth.getInstance().listUsers(null);

        String userEmail = "";
        for (ExportedUserRecord user : page.iterateAll()) {
            userEmail = user.getEmail();
            System.out.println("User: " + userEmail);
            uidList.add(userEmail);
        }

        return uidList;
    }

    /**
     * Firevase에 유저 등록 여부를 확인하는 메소드
     * @param: String email
     * @return: boolean
     **/
    public boolean isFirebaseUser(String email)  {

        ListUsersPage page = null;
        try {
            page = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }

        String userEmail = "";
        for (ExportedUserRecord user : page.iterateAll()) {
            userEmail = user.getEmail();
            if (userEmail.equals(email)){
                return true;
            }
        }

        return false;
    }

    /**
     * Firebase 유저를 삭제하는 메소드
     * @param: String email
     * @return: boolean
     **/
    public boolean deleteFirevaseUser(String email){
        ListUsersPage page = null;
        try {
            page = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }

        String userEmail = "";
        String userUid = "";
        for (ExportedUserRecord user : page.iterateAll()) {
            userEmail = user.getEmail();
            if (userEmail.equals(email)){
                userUid = user.getUid();
                break;
            }
        }

        try {
            FirebaseAuth.getInstance().deleteUser(userUid);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}
