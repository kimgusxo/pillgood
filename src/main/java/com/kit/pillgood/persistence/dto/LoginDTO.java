package com.kit.pillgood.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotEmpty(groups = {ValidationGroups.groupSearch.class}, message = "이메일 값은 필수 입니다.")
    @Email(groups = {ValidationGroups.groupSearch.class}, message = "이메일 형식으로 입력해주세요")
    private String userEmail;

    @NotEmpty(groups = {ValidationGroups.groupSearch.class}, message = "토큰 값은 필수 입니다.")
    private String userToken;

}
