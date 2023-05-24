package com.kit.pillgood.service;

import com.kit.pillgood.persistence.dto.DiseaseDTO;
import com.kit.pillgood.repository.DiseaseRepository;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiseaseService {
    private final Logger LOGGER = LoggerFactory.getLogger(DiseaseService.class);
    private final DiseaseRepository diseaseRepository;

    @Autowired
    public DiseaseService(DiseaseRepository diseaseRepository) {
        this.diseaseRepository = diseaseRepository;
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public DiseaseDTO searchDiseaseByDiseaseCode(String diseaseCode) {
        return EntityConverter.toDiseaseDTO(diseaseRepository.findByDiseaseCode(diseaseCode));
    }
}
