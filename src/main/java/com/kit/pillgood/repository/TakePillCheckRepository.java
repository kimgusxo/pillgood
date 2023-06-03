package com.kit.pillgood.repository;

import com.kit.pillgood.domain.TakePillCheck;
import com.kit.pillgood.persistence.dto.NotificationContentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TakePillCheckRepository  extends JpaRepository<TakePillCheck, Long> {

    @Modifying
    @Query("UPDATE TakePillCheck tpc SET tpc.takeCheck = :takeCheck WHERE tpc.takePillCheckIndex = :takePillCheckIndex")
    void updateTakeCheck(@Param("takePillCheckIndex") Long takePillCheckIndex, @Param("takeCheck") Boolean takeCheck);

}
