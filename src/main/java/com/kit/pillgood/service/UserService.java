package com.kit.pillgood.service;

import com.google.firebase.auth.*;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.exeptions.exeption.TransactionFailedException;
import com.kit.pillgood.exeptions.exeption.superExeption.EtcFirebaseException;
import com.kit.pillgood.persistence.dto.UserDTO;
import com.kit.pillgood.repository.UserRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO searchUser(String email) throws NonRegistrationUserException {
        User user = userRepository.findByUserEmail(email);

        if(user != null){
            UserDTO userDTO = EntityConverter.toUserDTO(userRepository.findByUserEmail(email));
            return userDTO;
        } else{
            throw new NonRegistrationUserException();
        }
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = EntityConverter.toUser(userDTO);
        userDTO = EntityConverter.toUserDTO(userRepository.save(user));
        LOGGER.info("[info] 유저 생성 완료 user={}", user );
        return userDTO;
    }

    @Transactional
    public UserDTO updateUserToken(Long userIndex, UserDTO userDTO) throws NonRegistrationUserException {
        try{
            User user = userRepository.findByUserIndex(userIndex);

            if(user == null) {
                throw new NonRegistrationUserException();
            }

            UserDTO updateUserDTO = settingUpdateUserData(userDTO, user);
            UserDTO newUserDTO = createUser(updateUserDTO);
            deleteUser(userIndex);
            LOGGER.info("[info] 유저 변경 완료 user={}", updateUserDTO );

            return newUserDTO;
        }
        catch (NonRegistrationUserException ignore){
            throw new NonRegistrationUserException();
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }
    }

    private UserDTO settingUpdateUserData(UserDTO userDTO, User user){
        UserDTO updateUserDTO = userDTO;
        if(updateUserDTO.getUserEmail() == null){
            updateUserDTO.setUserEmail(user.getUserEmail());
        }
        return updateUserDTO;
    }

    private void deleteUser(Long userIndex) throws NonRegistrationUserException {
        try{
            userRepository.deleteById(userIndex);
            LOGGER.info("[info] 유저 삭제 완료 userIndex={}", userIndex );
        }catch(IllegalArgumentException e){
            LOGGER.info("[err] userRepository.deleteById() 수행 오류 {}",e.getMessage());
            throw new NonRegistrationUserException();
        }
    }

    @Transactional
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

    @Transactional
    public boolean isFirebaseUser(String email) throws EtcFirebaseException {

        ListUsersPage page = null;
        try {
            page = FirebaseAuth.getInstance().listUsers(null);
        } catch (FirebaseAuthException e) {
            LOGGER.info("[err]  firebase api 오류 {}", e.getMessage());
            throw new EtcFirebaseException();
        }

        String userEmail = "";
        for (ExportedUserRecord user : page.iterateAll()) {
            userEmail = user.getEmail();
            if (userEmail.equals(email)){
                LOGGER.info("[info}]  firebaseUser={} exists", userEmail);
                return true;
            }
        }

        LOGGER.info("[info}]  firebaseUser={} notFound",  email);
        return false;
    }

    @Transactional
    public boolean deleteFirebaseUser(Long userIndex) throws EtcFirebaseException {
        try{

            User user = userRepository.findByUserIndex(userIndex);

            if(user == null){
                LOGGER.info(".deleteFirebaseUser [err] 등록된 유저 없음, !firebase에 등록 되어있을 가능성 존재 {}", userIndex);
                throw new NonRegistrationUserException();
            }

            String userEmail = user.getUserEmail();
            ListUsersPage page = null;
            try {
                page = FirebaseAuth.getInstance().listUsers(null);
            } catch (FirebaseAuthException e) {
                LOGGER.info(".deleteFirebaseUser [err] deleteFirebaseUser; firebase api 오류 {}", e.getMessage());
                throw new EtcFirebaseException();
            }

            String firebaseUserEmail = "";
            String firebaseUserUid = "";
            for (ExportedUserRecord authUser : page.iterateAll()) {
                firebaseUserEmail = authUser.getEmail();
                if (firebaseUserEmail.equals(userEmail)){
                    firebaseUserUid = authUser.getUid();
                    break;
                }
            }

            try {
                FirebaseAuth.getInstance().deleteUser(firebaseUserUid);
                LOGGER.info(".deleteFirebaseUser [info] firebase유저 삭제 완료 email:{}", firebaseUserEmail );
            } catch (IllegalArgumentException e) {
                LOGGER.info(".deleteFirebaseUser [err] firebase에 등록되지 않음 userEmail:{}", user.getUserEmail());
                throw new EtcFirebaseException();
            } catch (FirebaseAuthException e) {
                throw new RuntimeException(e);
            }

            deleteUser(userIndex);

            LOGGER.info(".deleteFirebaseUser [info] {} 삭제 완료",  user.getUserEmail());
            return true;
        }
        catch(EtcFirebaseException ignore){
            throw new EtcFirebaseException();
        }
        catch(NonRegistrationUserException ignore){
            LOGGER.info(".deleteFirebaseUser [err] 존재하지 않은 유저 검색");
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }
        return false;
    }
}
