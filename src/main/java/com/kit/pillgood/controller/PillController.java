package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonExistsPillIndexException;
import com.kit.pillgood.exeptions.exeption.NonExistsPillNameException;
import com.kit.pillgood.persistence.dto.SearchingConditionDTO;
import com.kit.pillgood.persistence.dto.ValidationGroups;
import com.kit.pillgood.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pill")
public class PillController {
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
    @GetMapping("/search/pill-index/{pill-index}")
    public ResponseEntity<ResponseFormat> getPillByPillIndex(@PathVariable(name="pill-index") Long pillIndex) throws NonExistsPillIndexException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), pillService.searchPillByPillIndex(pillIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 약 이름으로 약 검색
     * @param: Long pillIndex, 조회할 약 인덱스
     * @return: ResponseEntity<ResponseFormat>, 약 결과가 담긴 응답 객체
     **/
    @GetMapping("/search/pill-name/{pill-name}")
    public ResponseEntity<ResponseFormat> getPillByPillName(@PathVariable(name="pill-name") String pillName) throws NonExistsPillNameException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), pillService.searchPillByPillName(pillName));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 약 특징 정보로 약 리스트 검색
     * @param: SearchingConditionDTO searchingConditionDTO, 조회할 약 특징 정보
     * @return: ResponseEntity<ResponseFormat>, 약 리스트 결과가 담긴 응답 객체
     **/
    @PostMapping("/search/pills")
    public ResponseEntity<ResponseFormat> getSearchingPills(@RequestBody @Validated(ValidationGroups.groupSearch.class) SearchingConditionDTO searchingConditionDTO) throws NonExistsPillIndexException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), pillService.searchPillByAttributesOfPill(searchingConditionDTO));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
