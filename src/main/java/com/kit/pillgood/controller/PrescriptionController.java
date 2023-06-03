package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
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

    /**
     * 그룹원의 처방전 리스트 조회
     * @param: Long groupMemberIndex, 조회할 그룹원 인덱스
     * @return: ResponseEntity<ResponseFormat>, 처방전 리스트 결과가 담긴 응답 객체
     **/
    @GetMapping("/search/{group-member-index}")
    public ResponseEntity<ResponseFormat> getPrescriptionsByGroupMemberIndex(@PathVariable(name="group-member-index") Long groupMemberIndex) throws NonRegistrationGroupException {
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value(), prescriptionService.searchGroupMemberPrescriptionsByGroupMemberIndex(groupMemberIndex));
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 처방전 인덱스로 해당 처방전 삭제
     * @param: Long prescriptionIndex, 삭제할 처방전 인덱스
     * @return: ResponseEntity<ResponseFormat>, 삭제된 처방전 결과가 담긴 응답 객체
     **/
    @DeleteMapping("/delete/{prescription-index}")
    public ResponseEntity<ResponseFormat> deletePrescriptionByPrescriptionIndex(@PathVariable(name="prescription-index") Long prescriptionIndex) throws NonExistsPrescriptionIndexException {
        prescriptionService.deletePrescription(prescriptionIndex);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());
        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
