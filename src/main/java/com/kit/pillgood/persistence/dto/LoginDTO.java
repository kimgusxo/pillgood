package com.kit.pillgood.persistence.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class LoginDTO {

    @NotEmpty(groups = {ValidationGroups.groupSearch.class}, message = "정확한 형식의 이메일 값을 입력하세요")
    @Email(groups = {ValidationGroups.groupSearch.class})
    private String userEmail;

    @NotEmpty(groups = {ValidationGroups.groupSearch.class}, message = "토큰 값은 필수 입니다.")
    private String userToken;
}
