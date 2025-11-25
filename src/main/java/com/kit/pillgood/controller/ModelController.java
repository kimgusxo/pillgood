package com.kit.pillgood.controller;

import com.google.gson.JsonObject;
import com.kit.pillgood.persistence.dto.OriginalOcrDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/model")
public class ModelController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    private final RestTemplate restTemplate;

    @Autowired
    public ModelController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * OCR 결과를 얻기 위해 파이썬 모델 서버로 이미지 전송
     * @param: MultipartFile image, 모델서버로 전송할 이미지
     * @return: ResponseEntity<ResponseFormat>, OCR 결과가 담긴 응답 객체
     **/
    @PostMapping("/image")
    public OriginalOcrDTO sendImage(@RequestParam("image") MultipartFile image) {
        log.info("sendImage - 요청 수신, filename={}, size={}",
                image.getOriginalFilename(), image.getSize());

        try {
            byte[] imageBytes = image.getBytes();
            String encodedImage = Base64Utils.encodeToString(imageBytes);

            JsonObject json = new JsonObject();
            json.addProperty("image", encodedImage);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> requestEntity = new HttpEntity<>(json.toString(), headers);
            String url = "http://127.0.0.1:5000/ocr";

            log.debug("sendImage - Python OCR 서버 요청, url={}", url);
            ResponseEntity<OriginalOcrDTO> response =
                    restTemplate.exchange(url, HttpMethod.POST, requestEntity, OriginalOcrDTO.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("sendImage - OCR 서버 응답 성공, status={}", response.getStatusCode());
                return response.getBody();
            } else {
                log.warn("sendImage - OCR 서버 응답 실패, status={}", response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("sendImage - OCR 서버 호출 중 예외 발생", e);
        }
        return null;
    }
}





