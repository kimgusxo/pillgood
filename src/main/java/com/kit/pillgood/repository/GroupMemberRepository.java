package com.kit.pillgood.repository;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.persistence.projection.GroupMemberSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    GroupMemberSummary findByGroupMemberIndex(Long groupMemberIndex);
    List<GroupMemberSummary> findByUser_UserIndex(Long userIndex);
    void deleteByGroupMemberIndex(Long groupMemberIndex);

}
