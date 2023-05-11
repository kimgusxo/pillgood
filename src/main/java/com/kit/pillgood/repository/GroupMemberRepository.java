package com.kit.pillgood.repository;

import com.kit.pillgood.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    GroupMember findByGroupMemberIndex(Long groupMemberIndex);
    List<GroupMember> findByUser_UserIndex(Long userIndex);
    void deleteByGroupMemberIndex(Long groupMemberIndex);
}
