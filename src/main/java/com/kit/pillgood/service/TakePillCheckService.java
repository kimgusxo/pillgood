package com.kit.pillgood.service;

import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.domain.TakePillCheck;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.repository.TakePillCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TakePillCheckService {
    private final TakePillCheckRepository takePillCheckRepository;

    @Autowired
    public TakePillCheckService(TakePillCheckRepository takePillCheckRepository) {
        this.takePillCheckRepository = takePillCheckRepository;
    }

    public void createTakePillCheckByOCRData(List<Long> takePillIndexList, EditOcrDTO editOcrDTO) {

        List<TakePillCheck> takePillCheckList = new ArrayList<>();

        int count = 0;

        // takePillCheck 만들기
        for(Long takePillIndex : takePillIndexList) {
            for(Integer takePillTime : editOcrDTO.getPillList().get(count).getTakePillTimeList()) {
                TakePillCheck takePillCheck = TakePillCheck.builder()
                        .takePillCheckIndex(null)
                        .takePill(TakePill.builder()
                                .takePillIndex(takePillIndex)
                                .build())
                        .takeDate(editOcrDTO.getStartDate())
                        .takePillTime(takePillTime)
                        .takeCheck(false)
                        .build();
                takePillCheckList.add(takePillCheck);
            }
            count++;
        }

        takePillCheckRepository.saveAll(takePillCheckList);

    }
}
