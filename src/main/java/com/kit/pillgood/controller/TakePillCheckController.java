package com.kit.pillgood.controller;

import com.kit.pillgood.domain.TakePillCheck;
import com.kit.pillgood.persistence.dto.TakePillCheckDTO;
import com.kit.pillgood.service.TakePillCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/update/take-check")
    public void updateTakeCheck(@RequestParam Long takePillCheckIndex, @RequestParam Boolean takeCheck) {
        takePillCheckService.updateTakeCheck(takePillCheckIndex, takeCheck);
    }
}
