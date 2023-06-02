package com.kit.pillgood.repository;

import com.kit.pillgood.domain.Pill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PillRepository  extends JpaRepository<Pill, Long> {
    List<Pill> findPillsByPillNameAndPillShapeOrPillColorOrPillFrontWordOrPillBackWord(
            String pillName, String pillShape, String pillColor, String pillFrontWord, String pillBackWord);
    Pill findByPillIndex(Long pillIndex);
    Pill findByPillName(String pillName);

    // EditOcrData를 받을 때 사용자가 정의한 약이름이랑 DB에 정의된 약이름이랑 다를 경우가 생기기 때문에 유사도로 약을 검색해야 함
    @Query("SELECT p.pillName FROM Pill p WHERE p.pillName LIKE CONCAT('%', :searchKeyword, '%') " +
            "ORDER BY FUNCTION('LEVENSHTEIN', p.pillName, :searchKeyword) ASC")
    String findPillNameByPartiallyPillName(@Param("searchKeyword") String pillName);
}