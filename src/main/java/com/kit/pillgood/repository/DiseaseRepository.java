package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
        Disease findByDiseaseCode(String diseaseCode);
//        String findDiseaseNameByDiseaseIndex(Long diseaseIndex);
}
