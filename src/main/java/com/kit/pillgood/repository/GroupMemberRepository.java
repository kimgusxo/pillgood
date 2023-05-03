package com.kit.pillgood.repository;

import com.kit.pillgood.domain.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    GroupMember findByGroupMemberIndex(Long groupMemberIndex);
    void deleteByGroupMemberIndex(Long groupMemberIndex);
}
