package com.kit.pillgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TakePillControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "take-pill";

    @Test
    @DisplayName("캘린더 조회")
    void searchCalendar() throws Exception {
        //given
        Long userIndex = 1L;
        LocalDate dateStart = LocalDate.of(2023, 6, 3);
        LocalDate dateEnd = LocalDate.of(2023, 6, 6);

        //when

        //then
        mvc.perform(get(BASE_URL + "/search" + "/calendar-dara")
                        .param("userIndex", String.valueOf(userIndex))
                        .param("dateStart", String.valueOf(dateStart))
                        .param("dateEnd", String.valueOf(dateEnd)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("복용현황 조회")
    void searchTakePill() throws Exception {
        //given
        Long groupMemberIndex1 = 1L;
        Long groupMemberIndex2 = 2L;
        Long groupMemberIndex3 = 3L;
        LocalDate targetDate = LocalDate.of(2023, 6, 5);

        //when
        List<Long> groupMemberIndexList = new ArrayList<>();
        groupMemberIndexList.add(groupMemberIndex1);
        groupMemberIndexList.add(groupMemberIndex2);
        groupMemberIndexList.add(groupMemberIndex3);

        //then
        mvc.perform(post(BASE_URL + "/search")
                        .param("targetDate", String.valueOf(targetDate))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groupMemberIndexList)));

    }
}