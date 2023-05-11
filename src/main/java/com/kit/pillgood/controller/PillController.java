package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.PillDTO;
import com.kit.pillgood.persistence.dto.SearchingConditionDTO;
import com.kit.pillgood.service.PillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pill")
public class PillController {
    private final PillService pillService;

    @Autowired
    public PillController(PillService pillService) {
        this.pillService = pillService;
    }

    @GetMapping("/search/pill-index/{pill-index}")
    public PillDTO getPillByPillIndex(@PathVariable(name="pill-index") Long pillIndex) {
        return pillService.searchPillByPillIndex(pillIndex);
    }

    @GetMapping("/search/pill-name/{pill-name}")
    public PillDTO getPillByPillName(@PathVariable(name="pill-name") String pillName) {
        return pillService.searchPillByPillName(pillName);
    }

    @GetMapping("/search/pills")
    public List<PillDTO> getSearchingPills(@ModelAttribute SearchingConditionDTO searchingConditionDTO) {
        return pillService.searchPillByAttributesOfPill(searchingConditionDTO);
    }
}
