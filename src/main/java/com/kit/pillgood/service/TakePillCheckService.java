package com.kit.pillgood.service;

import com.kit.pillgood.domain.TakePill;
import com.kit.pillgood.domain.TakePillCheck;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.repository.TakePillCheckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TakePillCheckService {

    private final Logger LOGGER = LoggerFactory.getLogger(OCRService.class);
    private final TakePillCheckRepository takePillCheckRepository;

    @Autowired
    public TakePillCheckService(TakePillCheckRepository takePillCheckRepository) {
        this.takePillCheckRepository = takePillCheckRepository;
    }

    public void createTakePillCheckByOCRData(List<Long> takePillIndexList, EditOcrDTO editOcrDTO) throws SQLException {
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

        try{
            List<TakePillCheck> takePillCheckLists = takePillCheckRepository.saveAll(takePillCheckList);
            LOGGER.info(".createTakePillCheckByOCRData TakePillCheck 생성 완료 takePillCheckLists={}", takePillCheckLists);
        }
        catch (Exception ignore){
            LOGGER.info(".createTakePillCheckByOCRData [err] takePillCheckList 저장 실패 takePillCheckList={}", takePillCheckList);
            throw new SQLException();
        }



    }
}
