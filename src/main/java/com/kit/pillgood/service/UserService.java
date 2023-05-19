package com.kit.pillgood.service;

import com.google.firebase.auth.*;
import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.AlreadyExistUserException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.superExeption.AlreadyExistException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
     * 특정 유저 조회 메소드
     * @param: 이메일
     * @return: userDTO
     **/
    public UserDTO searchUser(String email) throws NonRegistrationUserException {
        User user = userRepository.findByUserEmail(email);

        if(user.getUserIndex() != null){
            UserDTO userDTO = EntityConverter.toUserDTO(userRepository.findByUserEmail(email));
            return userDTO;
        } else{
            throw new NonRegistrationUserException();
        }
    }

    /**
     * 유저를 생성하는 메소드
     * @param: 생성할 UserDTO
     * @return: DB에서 생성할 UserDTO
    **/
    public UserDTO createUser(UserDTO userDTO) {
        if(userRepository.findByUserEmail(userDTO.getUserEmail()) != null){
            try {
                deleteUser(userDTO.getUserIndex());
            } catch (NonRegistrationUserException ignore) {
            }
        }
        User user = EntityConverter.toUser(userDTO);
        userDTO = EntityConverter.toUserDTO(userRepository.save(user));
        return userDTO;
    }

    /**
     * 유저 업데이트 메소드
     * @param: 변경된 UserDTO
     * @return: DB에서 변경된 UserDTO
     **/
    @Transactional
    public UserDTO updateUserToken(UserDTO userDTO) throws NonRegistrationUserException {
        Long userIndex = userDTO.getUserIndex();
        User user = userRepository.findByUserIndex(userIndex);

        if(user != null) {
            deleteUser(userIndex);
            return createUser(userDTO);
        }
        else{
            throw new NonRegistrationUserException();
        }
    }


    /**
     * 유저 삭제 메소드
     * @param: 유저 인덱스
     * @return: void
     **/
    public boolean deleteUser(Long userIndex) throws NonRegistrationUserException {
        try{
            userRepository.deleteById(userIndex);
        }catch(IllegalArgumentException ignored){
            throw new NonRegistrationUserException();
        }
        return true;
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
    public boolean isFirebaseUser(String email) throws EtcFirebaseException {

        ListUsersPage page = null;
        try {
            page = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            throw new EtcFirebaseException();
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
    public boolean deleteFirebaseUser(UserDTO userDTO) throws EtcFirebaseException, NonRegistrationUserException {
        Long userIndex = userDTO.getUserIndex();
        String userEmail = userDTO.getUserEmail();
        ListUsersPage page = null;
        try {
            page = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            throw new EtcFirebaseException();
        }

        String firebaseUserEmail = "";
        String firebaseUserUid = "";
        for (ExportedUserRecord user : page.iterateAll()) {
            firebaseUserEmail = user.getEmail();
            if (firebaseUserEmail.equals(userEmail)){
                firebaseUserUid = user.getUid();
                break;
            }
        }

        try {
            FirebaseAuth.getInstance().deleteUser(firebaseUserUid);
        } catch (FirebaseAuthException e) {
            throw new EtcFirebaseException();
        }

        deleteUser(userIndex);

        return true;
    }


}
