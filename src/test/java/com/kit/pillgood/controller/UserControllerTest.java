package com.kit.pillgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.pillgood.persistence.dto.UserDTO;
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
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "/user";

    @Test
    @DisplayName("유저 삭제")
    void deleteUser() throws Exception {
        //given
        Long userIndex = 1L;

        //when

        //then
        mvc.perform(delete(BASE_URL + "/delete/" + userIndex)
                .param("userIndex", String.valueOf(userIndex))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저 FCM 토큰 업데이트")
    void updateUserToken() throws Exception {
        //given
        Long userIndex = 1L;
        String userEmail = "user1@test.com";
        String userFcmToken = "token123";

        //when
        UserDTO userDTO = UserDTO.builder()
                .userIndex(userIndex)
                .userEmail(userEmail)
                .userFcmToken(userFcmToken)
                .build();

        //then
        mvc.perform(put(BASE_URL + "/update-token /" + userIndex)
                .param("userIndex", String.valueOf(userIndex))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO))
        ).andExpect(status().isOk());
    }

}