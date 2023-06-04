package com.kit.pillgood.service;

import com.kit.pillgood.domain.TakePillCheck;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.repository.TakePillCheckRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void createTakePillCheckByOCRData(List<Long> takePillIndexList, EditOcrDTO editOcrDTO) throws SQLException {
        List<TakePillCheck> takePillCheckList = new ArrayList<>();

        int count = 0;

        // takePillCheck 만들기
        for(Long takePillIndex : takePillIndexList) {
            for(Integer takePillTime : editOcrDTO.getPillList().get(count).getTakePillTimeList()) {
                TakePillCheck takePillCheck = EntityConverter.toTakePillCheck(takePillIndex, takePillTime, editOcrDTO);
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

    @Transactional
    public void updateTakeCheck(List<Long> takePillCheckIndexList, Boolean takeCheck) {
        takePillCheckRepository.updateTakeCheck(takePillCheckIndexList, takeCheck);
    }
}
