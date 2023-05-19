package com.kit.pillgood.service;

import com.kit.pillgood.domain.GroupMember;
import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.persistence.dto.GroupMemberDTO;
import com.kit.pillgood.persistence.dto.PillDTO;
import com.kit.pillgood.persistence.dto.SearchingConditionDTO;
import com.kit.pillgood.repository.PillRepository;
import com.kit.pillgood.util.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PillService {
    private final PillRepository pillRepository;

    @Autowired
    public PillService(PillRepository pillRepository) {
        this.pillRepository = pillRepository;
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public PillDTO searchPillByPillIndex(Long pillIndex) {
        PillDTO pillDTO = EntityConverter.toPillDTO(pillRepository.findByPillIndex(pillIndex));
        return pillDTO;
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public PillDTO searchPillByPillName(String pillName) {
        PillDTO pillDTO = EntityConverter.toPillDTO(pillRepository.findByPillName(pillName));
        return pillDTO;
    }

    /**
     * 메소드의 간략한 설명
     * @param: 파라미터 설명
     * @return: 리턴 값 설명
    **/
    public List<PillDTO> searchPillByAttributesOfPill(SearchingConditionDTO searchingConditionDTO) {

        List<Pill> pills = pillRepository.findPillsByPillNameAndPillShapeOrPillColorOrPillFrontWordOrPillBackWord(
                searchingConditionDTO.getPillName(),
                searchingConditionDTO.getPillColor(),
                searchingConditionDTO.getPillShape(),
                searchingConditionDTO.getPillFrontWord(),
                searchingConditionDTO.getPillBackWord());

        List<PillDTO> pillDTOs = new ArrayList<>();

        for(Pill pill : pills) {
            PillDTO pillDTO = EntityConverter.toPillDTO(pill);
            pillDTOs.add(pillDTO);
        }

        return pillDTOs;
    }
}
