package com.kit.pillgood.controller;

import com.kit.pillgood.exeptions.exeption.NonRegistrationUserException;
import com.kit.pillgood.persistence.dto.MedicationInfoDTO;
import com.kit.pillgood.persistence.dto.TakePillAndTakePillCheckAndGroupMemberIndexDTO;
import com.kit.pillgood.service.TakePillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    public List<TakePillAndTakePillCheckAndGroupMemberIndexDTO> getCalendarDataByUserIndexBetweenDate(@RequestParam Long userIndex,
                                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateStart,
                                                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateEnd) throws NonRegistrationUserException {
        return takePillService.searchTakePillCheckListByUserIndexBetweenTakeDate(userIndex, dateStart, dateEnd);
    }

    @PostMapping("/search")
    public List<MedicationInfoDTO> getTakePillsByGroupMemberIndexListAndTargetDate(@RequestBody List<Long> groupMemberIndexList,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate targetDate) {
        return takePillService.searchMedicationInfoListByGroupMemberIndexListAndTargetDate(groupMemberIndexList, targetDate);
    }

//    @GetMapping("/initial-data")
//    public List<InitialCalendarAndTakePillsInfoDTO> getInitialCalenderAndTakePillsBuUserIndexBetweenDate(@RequestParam Long userIndex,
//                                                                                                         @RequestParam LocalDate dateStart,
//                                                                                                         @RequestParam LocalDate dateCur,
//                                                                                                         @RequestParam LocalDate dateEnd) {
//
//    }

}
