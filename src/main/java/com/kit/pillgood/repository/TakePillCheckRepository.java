package com.kit.pillgood.repository;

import com.kit.pillgood.domain.TakePillCheck;
import com.kit.pillgood.persistence.dto.NotificationContentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TakePillCheckRepository  extends JpaRepository<TakePillCheck, Long> {

}
