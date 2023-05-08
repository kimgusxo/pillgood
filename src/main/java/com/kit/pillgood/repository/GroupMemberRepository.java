package com.kit.pillgood.repository;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.domain.User;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import com.kit.pillgood.persistence.projection.GroupMemberAndUserIndexSummary;
import com.kit.pillgood.persistence.projection.GroupMemberSummary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    GroupMemberAndUserIndexSummary save(GroupMemberAndUserIndexSummary groupMemberAndUserIndexSummary);
    GroupMember findByGroupMemberIndex(Long groupMemberIndex);
    GroupMemberSummary findGroupMemberByGroupMemberIndex(Long groupMemberIndex);
    List<GroupMemberSummary> findGroupMembersByUser(User user);
    void deleteByGroupMemberIndex(Long groupMemberIndex);

}
