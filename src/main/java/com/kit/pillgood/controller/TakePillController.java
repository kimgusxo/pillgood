package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.MedicationInfoDTO;
import com.kit.pillgood.persistence.dto.TakePillAndTakePillCheckAndGroupMemberIndexDTO;
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

    @GetMapping("/search/calendar-data")
    public ResponseEntity<ResponseFormat> getCalendarDataByUserIndexBetweenDate(@RequestParam Long userIndex,
                                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd) throws NonRegistrationUserException {
        ResponseFormat responseFormat = ResponseFormat.of("성공 코드", HttpStatus.OK.value(), takePillService.searchTakePillCheckListByUserIndexBetweenTakeDate(userIndex, dateStart, dateEnd));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<ResponseFormat> getTakePillsByGroupMemberIndexListAndTargetDate(@RequestBody List<Long> groupMemberIndexList,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {

        ResponseFormat responseFormat = ResponseFormat.of("성공 코드", HttpStatus.OK.value(), takePillService.searchMedicationInfoListByGroupMemberIndexListAndTargetDate(groupMemberIndexList, targetDate));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

//    @GetMapping("/initial-data")
//    public List<InitialCalendarAndTakePillsInfoDTO> getInitialCalenderAndTakePillsBuUserIndexBetweenDate(@RequestParam Long userIndex,
//                                                                                                         @RequestParam LocalDate dateStart,
//                                                                                                         @RequestParam LocalDate dateCur,
//                                                                                                         @RequestParam LocalDate dateEnd) {
//
//    }

}
