package com.kit.pillgood.controller;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringEscapeUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kit.pillgood.persistence.dto.OriginalOcrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;

@RestController
@RequestMapping("/model")
public class ModelController {
    private final RestTemplate restTemplate;

    @Autowired
    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("")
    public OriginalOcrDTO sendImage(@RequestParam MultipartFile image) {

        try {
            // 이미지 데이터를 Base64 인코딩하여 문자열로 변환
            String encodedImage = Base64.getEncoder().encodeToString(image.getBytes());
            System.out.println("요청 실패: 1");

            // JSON 객체 생성 및 이미지 데이터 추가
            JsonObject json = new JsonObject ();
            json.addProperty("image", encodedImage);
            System.out.println("요청 실패: 2");

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            System.out.println("요청 실패: 3");
 
            // HTTP 요청 본문 설정
            HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);
            System.out.println("요청 실패: 4");

            // 파이썬 모델 서버 URL
            String url = "http://127.0.0.1:5000/ocr";
            System.out.println("요청 실패: 5");

            // POST 요청 보내기
            ResponseEntity<OriginalOcrDTO> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, OriginalOcrDTO.class);
            System.out.println("요청 실패: 6");
            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                OriginalOcrDTO OCRResult = response.getBody();

                System.out.println("요청 실패: 7");

                System.out.println(OCRResult);
//                String responseBody = response.getBody();
                System.out.println("responseBody");
                System.out.println(OCRResult);

//                ObjectMapper objectMapper = new ObjectMapper();
//                OriginalOcrDTO resultOCR = objectMapper.readValue(responseBody, new TypeReference<OriginalOcrDTO>() {});

                System.out.println("요청 실패: 8");
                System.out.println("결과 ~~~~~~~~~~~~~~");
                System.out.println(OCRResult.getHospitalName());
                System.out.println(OCRResult.getPhoneNumber());
                System.out.println(OCRResult.getDiseaseCode());
                System.out.println(OCRResult.getPillNameList());
                System.out.println(OCRResult.getPillNameList().get(0).getTakePillTimeList().get(0));

                return null;
            } else {
                System.out.println("요청 실패: 9");
                // 응답 실패 시 처리 로직 작성
                System.out.println("요청 실패: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.out.println("요청 실패: 9 ");
            System.out.println("요청 실패: 2 " + e.getMessage());
            System.out.println("요청 실패: 22222" + e);
        }

        System.out.println("요청 실패: 3" );

        return null;
    }
}





