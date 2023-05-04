package com.kit.pillgood.controller;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Prescription;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import com.kit.pillgood.persistence.dto.PrescriptionDTO;
import com.kit.pillgood.service.PrescriptionService;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

//    @GetMapping("/search/{group-member-index}")
//    public List<PrescriptionDTO> getPrescriptionsByGroupMemberIndex(@PathVariable(name="group-member-index") Long groupMemberIndex) {
//        return prescriptionService.searchGroupMemberPrescriptionsByGroupMemberIndex(groupMemberIndex);
//    }

    @PostMapping("/create/image-upload")
    public PrescriptionDTO createPrescriptionByImage(@ModelAttribute MultipartFile prescriptionImage) {
        return null;
    }

    @DeleteMapping("/delete/{prescription-index}")
    public PrescriptionDTO deletePrescriptionByPrescriptionIndex(@PathVariable(name="prescription-index") Long prescriptionIndex) {
        return null;
    }
}
