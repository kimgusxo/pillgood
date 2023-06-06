package com.kit.pillgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
class PrescriptionControllerTest1 {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "/prescription";

    @Test
    @DisplayName("그룹원 처방전 조회")
    void searchPrescription() throws Exception {
        //given
        Long groupMemberIndex = 1L;

        //when

        //then
        mvc.perform(get(BASE_URL + "/search/" + groupMemberIndex)
                        .param("groupMemberIndex", String.valueOf(groupMemberIndex)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("처방전 삭제")
    void deletePrescription() throws Exception {
        //given
        Long prescriptionIndex = 1L;

        //when

        //then
        mvc.perform(delete(BASE_URL + "/delete/" + prescriptionIndex)
                        .param("prescriptionIndex", String.valueOf(prescriptionIndex)))
                .andExpect(status().isOk());
    }
}