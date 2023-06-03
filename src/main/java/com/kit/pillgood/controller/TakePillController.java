package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonExistsMedicationInfoException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.service.TakePillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/take-pill")
public class TakePillController {
    private final TakePillService takePillService;

    @Autowired
    public TakePillController(TakePillService takePillService) {
        this.takePillService = takePillService;
    }

    /**
     * 시작 날짜부터 끝 날짜까지의 그룹원들의 복용 정보 조회
     * @param: Long userIndex, 복용 정보를 조회할 사용자 인덱스
     * @param: LocalDate dateStart, 복용 정보의 범위를 설정하기 위한 시작날짜
     * @param: LocalDate dateEnd, 복용 정보의 범위를 설정하기 위한 끝날짜
     * @return: ResponseEntity<ResponseFormat>, 복용 정보 결과가 담긴 응답 객체
     **/
    @GetMapping("/search/calendar-data")
    public ResponseEntity<ResponseFormat> getCalendarDataByUserIndexBetweenDate(@RequestParam Long userIndex,
                                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), takePillService.searchTakePillCheckListByUserIndexBetweenTakeDate(userIndex, dateStart, dateEnd));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 해당 날짜의 그룹원들의 복용 정보 상세 조회
     * @param: List<Long> groupMemberIndexList, 복용 상세 정보를 조회할 그룹원 리스트
     * @param: LocalDate dateStart, 복용 상세 정보를 조회할 해당 날짜
     * @return: ResponseEntity<ResponseFormat>, 복용 상세 정보 결과가 담긴 응답 객체
     **/
    @PostMapping("/search")
    public ResponseEntity<ResponseFormat> getTakePillsByGroupMemberIndexListAndTargetDate(@RequestBody List<Long> groupMemberIndexList,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) throws NonExistsMedicationInfoException {

        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), takePillService.searchMedicationInfoListByGroupMemberIndexListAndTargetDate(groupMemberIndexList, targetDate));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

}
