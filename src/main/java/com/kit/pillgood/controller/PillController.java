package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonExistsPillIndexException;
import com.kit.pillgood.exeptions.exeption.NonExistsPillNameException;
import com.kit.pillgood.persistence.dto.SearchingConditionDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.PillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pills")
public class PillController {

    private static final Logger log = LoggerFactory.getLogger(PillController.class);

    private final PillService pillService;

    @Autowired
    public PillController(PillService pillService) {
        this.pillService = pillService;
    }

    /**
     * 약 인덱스로 약 검색
     * @param: Long pillIndex, 조회할 약 인덱스
     * @return: ResponseEntity<ResponseFormat>, 약 결과가 담긴 응답 객체
     **/
    @GetMapping("/{pillIndex}")
    public ResponseEntity<ResponseFormat> getPillByPillIndex(@PathVariable(name="pillIndex") Long pillIndex) throws NonExistsPillIndexException {
        log.info("getPillByPillIndex - 요청 수신, pillIndex={}", pillIndex);

        ResponseFormat responseFormat = ResponseFormat.of(
                "success",
                HttpStatus.OK.value(),
                pillService.searchPillByPillIndex(pillIndex)
        );

        log.debug("getPillByPillIndex - 조회 성공, pillIndex={}", pillIndex);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 약 이름으로 약 검색
     * @param: Long pillIndex, 조회할 약 인덱스
     * @return: ResponseEntity<ResponseFormat>, 약 결과가 담긴 응답 객체
     **/
    @GetMapping("/pillName")
    public ResponseEntity<ResponseFormat> getPillByPillName(@RequestParam("pillName") String pillName) throws NonExistsPillNameException {
        log.info("getPillByPillName - 요청 수신, pillName={}", pillName);

        ResponseFormat responseFormat = ResponseFormat.of(
                "success",
                HttpStatus.OK.value(),
                pillService.searchPillByPillName(pillName)
        );

        log.debug("getPillByPillName - 조회 성공, pillName={}", pillName);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 약 특징 정보로 약 리스트 검색
     * @param: SearchingConditionDTO searchingConditionDTO, 조회할 약 특징 정보
     * @return: ResponseEntity<ResponseFormat>, 약 리스트 결과가 담긴 응답 객체
     **/
    @PostMapping("/search")
    public ResponseEntity<ResponseFormat> getSearchingPills(@RequestBody @Validated(ValidationGroups.groupSearch.class) SearchingConditionDTO searchingConditionDTO) throws NonExistsPillIndexException {
        log.info("getSearchingPills - 요청 수신, condition={}", searchingConditionDTO);

        ResponseFormat responseFormat = ResponseFormat.of(
                "success",
                HttpStatus.OK.value(),
                pillService.searchPillByAttributesOfPill(searchingConditionDTO)
        );

        log.debug("getSearchingPills - 검색 성공, condition={}", searchingConditionDTO);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
