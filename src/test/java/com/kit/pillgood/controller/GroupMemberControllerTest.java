package com.kit.pillgood.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kit.pillgood.persistence.dto.GroupMemberAndUserIndexDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GroupMemberControllerTest {

    @Autowired
    MockMvc mvc;

    private static final String BASE_URL = "/group-member";

    @Test
    @DisplayName("그룹원 생성 테스트")
    void create_test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //given
        LocalDate groupMemberBirth = LocalDate.of(1999, 6, 30);
        String groupMemberName = "김현태";
        String groupMemberPhone = "010-7104-9906";
        Long userIndex = 1L;
        Boolean messageCheck = false;

        //when
        GroupMemberAndUserIndexDTO groupMemberAndUserIndexDTO = GroupMemberAndUserIndexDTO.builder()
                        .groupMemberBirth(groupMemberBirth)
                        .groupMemberName(groupMemberName)
                        .groupMemberPhone(groupMemberPhone)
                        .userIndex(userIndex)
                        .messageCheck(messageCheck)
                        .build();

        //then
        mvc.perform(post(BASE_URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(groupMemberAndUserIndexDTO))
        ).andExpect(status().isOk());
    }
}