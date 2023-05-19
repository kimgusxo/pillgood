package com.kit.pillgood.repository;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserIndex(Long userIndex);
    User findByUserEmail(String email);
    Boolean findByUserIndexAndGroupPhoneNumber(Long userIndex, String groupPhoneNumber);
}
