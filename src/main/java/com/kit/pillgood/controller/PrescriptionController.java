package com.kit.pillgood.controller;

import com.kit.pillgood.common.ResponseFormat;
import com.kit.pillgood.exeptions.exeption.NonExistsPrescriptionIndexException;
import com.kit.pillgood.exeptions.exeption.NonRegistrationGroupException;
import com.kit.pillgood.service.PrescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private static final Logger log = LoggerFactory.getLogger(PrescriptionController.class);

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
    @GetMapping
    public ResponseEntity<ResponseFormat> getPrescriptionsByGroupMemberIndex(@RequestParam("groupMemberIndex") Long groupMemberIndex) throws NonRegistrationGroupException {
        log.info("getPrescriptionsByGroupMemberIndex - 요청 수신, groupMemberIndex={}", groupMemberIndex);

        ResponseFormat responseFormat = ResponseFormat.of(
                "success",
                HttpStatus.OK.value(),
                prescriptionService.searchGroupMemberPrescriptionsByGroupMemberIndex(groupMemberIndex)
        );

        log.debug("getPrescriptionsByGroupMemberIndex - 조회 성공, groupMemberIndex={}", groupMemberIndex);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }

    /**
     * 처방전 인덱스로 해당 처방전 삭제
     * @param: Long prescriptionIndex, 삭제할 처방전 인덱스
     * @return: ResponseEntity<ResponseFormat>, 삭제된 처방전 결과가 담긴 응답 객체
     **/
    @DeleteMapping("/{prescriptionIndex}")
    public ResponseEntity<ResponseFormat> deletePrescriptionByPrescriptionIndex(@PathVariable(name="prescriptionIndex") Long prescriptionIndex) throws NonExistsPrescriptionIndexException {
        log.info("deletePrescriptionByPrescriptionIndex - 요청 수신, prescriptionIndex={}", prescriptionIndex);

        prescriptionService.deletePrescription(prescriptionIndex);
        ResponseFormat responseFormat = ResponseFormat.of("success", HttpStatus.OK.value());

        log.debug("deletePrescriptionByPrescriptionIndex - 삭제 성공, prescriptionIndex={}", prescriptionIndex);

        return new ResponseEntity<>(responseFormat, HttpStatus.OK);
    }
}
