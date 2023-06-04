package com.kit.pillgood.controller;

import com.kit.pillgood.service.TakePillCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/take-pill-check")
public class TakePillCheckController {
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
    @PutMapping("/update/take-check")
    public void updateTakeCheck(@RequestBody List<Long> takePillCheckIndexList, @RequestParam Boolean takeCheck) {
        takePillCheckService.updateTakeCheck(takePillCheckIndexList, takeCheck);
    }
}
