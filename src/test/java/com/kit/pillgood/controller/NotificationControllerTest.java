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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "/notification";

    @Test
    @DisplayName("알림 리스트 조회")
    void searchNotification() throws Exception {
        //given
        Long userIndex = 1L;

        //when

        //then
        mvc.perform(get(BASE_URL + "/search/" + userIndex)
                .param("userIndex", String.valueOf(userIndex))
                ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("알림 확인 여부 수정")
    void updateNotificationCheck() throws Exception {
        //given
        Long notificationIndex = 1L;

        //when

        //then
        mvc.perform(get(BASE_URL + "/update/" + notificationIndex)
                .param("notificationIndex", String.valueOf(notificationIndex))
        ).andExpect(status().isOk());
    }
}