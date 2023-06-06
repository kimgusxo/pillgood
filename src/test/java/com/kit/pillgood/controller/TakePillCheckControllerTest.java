package com.kit.pillgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TakePillCheckControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "take-pill-check";

    @Test
    @DisplayName("복용현황 수정")
    void updateTakeCheck() throws Exception {
        //given
        Long takePillCheckIndex1 = 1L;
        Long takePillCheckIndex2 = 2L;
        Long takePillCheckIndex3 = 3L;

        Boolean takeCheck = true;

        //when
        List<Long> takePillCheckIndexList = new ArrayList<>();
        takePillCheckIndexList.add(takePillCheckIndex1);
        takePillCheckIndexList.add(takePillCheckIndex2);
        takePillCheckIndexList.add(takePillCheckIndex3);

        //then
        mvc.perform(put(BASE_URL + "/update" + "/take-check")
                .param("takeCheck", String.valueOf(takeCheck))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(takePillCheckIndexList))
        ).andExpect(status().isOk());
    }
}