package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.InitialCalendarAndTakePillsInfoDTO;
import com.kit.pillgood.persistence.dto.MedicationInfoDTO;
import com.kit.pillgood.persistence.dto.TakePillCheckAndGroupMemberIndexDTO;
import com.kit.pillgood.service.TakePillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
//re
//    @GetMapping("/search/calendar-data")
//    public List<TakePillCheckAndGroupMemberIndexDTO> getCalendarDataByUserIndexBetweenDate(@RequestParam Long userIndex,
//                                                                                                           @RequestParam LocalDate dateStart,
//                                                                                                           @RequestParam LocalDate dateEnd) {
//        takePillService.searchTakePillCheckListByUserIndexBetweenTakeDate(userIndex, dateStart, dateEnd);
//    }
//
//    @GetMapping("")
//    public List<MedicationInfoDTO> getTakePillsByUserIndexAndDate(@RequestParam Long userIndex,
//                                                                                  @RequestParam LocalDate date) {
//
//    }
//
//    @GetMapping("/initial-data")
//    public List<InitialCalendarAndTakePillsInfoDTO> getInitialCalenderAndTakePillsBuUserIndexBetweenDate(@RequestParam Long userIndex,
//                                                                                                                         @RequestParam LocalDate dateStart,
//                                                                                                                         @RequestParam LocalDate dateCur,
//                                                                                                                         @RequestParam LocalDate dateEnd) {
//
//    }
}
