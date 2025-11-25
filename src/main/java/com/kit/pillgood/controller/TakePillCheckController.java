package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.service.TakePillCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/takePillChecks")
public class TakePillCheckController {

    private static final Logger log = LoggerFactory.getLogger(TakePillCheckController.class);

    private final TakePillCheckService takePillCheckService;

    @Autowired
    public TakePillCheckController(TakePillCheckService takePillCheckService) {
        this.takePillCheckService = takePillCheckService;
    }

    /**
     * 약의 복용 현황 수정
     * @param: Long takePillCheckIndex, 복용 현황을 수정할 복용 현황 확인 인덱스
     * @param: Boolean takeCheck, 수정할 복용 현황 확인 값
     * @return: ResponseEntity<ResponseFormat>, 복용 현황 확인 결과가 담긴 응답 객체
     **/
    @PutMapping
    public ResponseEntity<ResponseFormat> updateTakeCheck(@RequestBody List<Long> takePillCheckIndexList, @RequestParam Boolean takeCheck) {
        log.info("updateTakeCheck - 요청 수신, size={}, takeCheck={}",
                takePillCheckIndexList != null ? takePillCheckIndexList.size() : 0,
                takeCheck);

        takePillCheckService.updateTakeCheck(takePillCheckIndexList, takeCheck);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());

        log.debug("updateTakeCheck - 복용 현황 수정 완료, indexList={}", takePillCheckIndexList);
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
