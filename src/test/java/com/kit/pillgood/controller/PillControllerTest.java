package com.kit.pillgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.pillgood.persistence.dto.SearchingConditionDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PillControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "/pill";

    @Test
    @DisplayName("약 인덱스 조회")
    void searchPillIndex() throws Exception {
        //given
        Long pillIndex = 1L;

        //when

        //then
        mvc.perform(get(BASE_URL + "/search/" + pillIndex)
                .param("pillIndex", String.valueOf(pillIndex)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("약 이름 조회")
    void searchPillName() throws Exception {
        //given
        String pillName = "레보투스";

        //when

        //then
        mvc.perform(get(BASE_URL + "/search/" + pillName)
                .param("pillName", pillName))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("약 조회")
    void searchPill() throws Exception {
        //given
        String pillName = "";
        String pillShape = "장방형";
        String pillColor = "분홍";
        String pillFrontWord = "";
        String pillBackWord = "";


        //when
        SearchingConditionDTO searchingConditionDTO = SearchingConditionDTO.builder()
                .pillName(pillName)
                .pillShape(pillShape)
                .pillColor(pillColor)
                .pillFrontWord(pillFrontWord)
                .pillBackWord(pillBackWord)
                .build();


        //then
        mvc.perform(post(BASE_URL + "/search" + "pills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(searchingConditionDTO)));

    }
}