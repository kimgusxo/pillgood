package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PillRepository  extends JpaRepository<Pill, Long> {
    List<Pill> findPillsByPillNameAndPillShapeOrPillColorOrPillFrontWordOrPillBackWord(
            String pillName, String pillShape, String pillColor, String pillFrontWord, String pillBackWord);
    Pill findByPillIndex(Long pillIndex);
    Pill findByPillName(String pillName);
}
