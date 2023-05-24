package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.persistence.dto.PrescriptionAndDiseaseNameDTO;
import com.kit.pillgood.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @Autowired
    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @GetMapping("/search/{group-member-index}")
    public ResponseEntity<ResponseFormat> getPrescriptionsByGroupMemberIndex(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), prescriptionService.searchGroupMemberPrescriptionsByGroupMemberIndex(groupMemberIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

//    @PostMapping("/create/image-upload")
//    public PrescriptionDTO createPrescriptionByImage(@RequestParam Long userIndex,
//                                                     @RequestParam Long groupMemberIndex,
//                                                     @ModelAttribute MultipartFile prescriptionImage) {
//        return prescriptionService.createPrescription(userIndex, groupMemberIndex, prescriptionImage);
//    }

    @DeleteMapping("/delete/{prescription-index}")
    public ResponseEntity<ResponseFormat> deletePrescriptionByPrescriptionIndex(@PathVariable(name="prescription-index") Long prescriptionIndex) throws NonExistsPrescriptionIndexException {
        prescriptionService.deletePrescription(prescriptionIndex);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
