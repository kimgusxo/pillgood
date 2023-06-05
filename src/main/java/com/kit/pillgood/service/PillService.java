package com.kit.pillgood.service;

import com.kit.pillgood.domain.Pill;
import com.kit.pillgood.exeptions.exeption.NonExistsPillIndexException;
import com.kit.pillgood.exeptions.exeption.NonExistsPillNameException;
import com.kit.pillgood.exeptions.exeption.TransactionFailedException;
import com.kit.pillgood.persistence.dto.EditOcrDTO;
import com.kit.pillgood.persistence.dto.PillDTO;
import com.kit.pillgood.persistence.dto.PillScheduleDTO;
import com.kit.pillgood.persistence.dto.SearchingConditionDTO;
import com.kit.pillgood.repository.PillRepository;
import com.kit.pillgood.specification.PillSpecification;
import com.kit.pillgood.util.EntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PillService {
    private final Logger LOGGER = LoggerFactory.getLogger(PillService.class);
    private final PillRepository pillRepository;

    @Autowired
    public PillService(PillRepository pillRepository) {
        this.pillRepository = pillRepository;
    }

    @Transactional
    public PillDTO searchPillByPillIndex(Long pillIndex) throws NonExistsPillIndexException {
        try{
            Pill pill = pillRepository.findByPillIndex(pillIndex);
            if(pill == null){
                LOGGER.info(".searchPillByPillIndex [err] pillIndex={}를 찾을 수 없음", pillIndex);
                throw new NonExistsPillIndexException();
            }
            PillDTO pillDTO = EntityConverter.toPillDTO(pill);
            LOGGER.info(".searchPillByPillIndex pillIndex={} 조회 성공", pillIndex);
            return pillDTO;
        }
        catch (NonExistsPillIndexException ignore){
            throw new NonExistsPillIndexException();
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }
    }

    @Transactional
    public PillDTO searchPillByPillName(String pillName) throws NonExistsPillNameException {
        try{
            Pill pill = pillRepository.findByPillName(pillName);
            if(pill == null){
                LOGGER.info(".searchPillByPillName [err] pillName={}을 찾을 수 없음", pillName);
                throw new NonExistsPillNameException();
            }
            PillDTO pillDTO = EntityConverter.toPillDTO(pill);
            LOGGER.info(".searchPillByPillName pillIndex={} 조회 성공", pillName);
            return pillDTO;
        }
        catch (NonExistsPillNameException ignore){
            throw new NonExistsPillNameException();
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }
    }

    @Transactional
    public EditOcrDTO searchPillNameByPartiallyPillName(EditOcrDTO editOcrDTO) {
        try{
            for(PillScheduleDTO pillScheduleDTO : editOcrDTO.getPillList()) {
                String pillName = pillRepository.findPillNameByPartiallyPillName(pillScheduleDTO.getPillName());
                pillScheduleDTO.setPillName(pillName);
            }
            return editOcrDTO;
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }
    }

    @Transactional
    public List<PillDTO> searchPillByAttributesOfPill(SearchingConditionDTO searchingConditionDTO) throws NonExistsPillIndexException {
        try{
            List<Pill> pillList = new ArrayList<>();
            if(searchingConditionDTO.getPillName() != null && !searchingConditionDTO.getPillName().isEmpty()) {
                int count = 0;
                List<Pill> tempPillList = pillRepository.findPillListByPillName(searchingConditionDTO.getPillName());
                for(Pill pill : tempPillList) {
                    if(pill.getPillShape().equals(searchingConditionDTO.getPillShape()) && !searchingConditionDTO.getPillShape().isEmpty()) {
                        pillList.add(tempPillList.get(count));
                    } else if(pill.getPillColor().equals(searchingConditionDTO.getPillColor()) && !searchingConditionDTO.getPillColor().isEmpty()) {
                        pillList.add(tempPillList.get(count));
                    } else if(pill.getPillFrontWord().equals(searchingConditionDTO.getPillFrontWord()) && !searchingConditionDTO.getPillFrontWord().isEmpty()) {
                        pillList.add(tempPillList.get(count));
                    } else if(pill.getPillBackWord().equals(searchingConditionDTO.getPillBackWord()) && !searchingConditionDTO.getPillBackWord().isEmpty()) {
                        pillList.add(tempPillList.get(count));
                    }
                    count++;
                }
            } else {
                PillSpecification pillSpecification = new PillSpecification(searchingConditionDTO);
                pillList = pillRepository.findAll(pillSpecification);
            }
            if(pillList.isEmpty()){
                LOGGER.info(".searchPillByAttributesOfPill Pill이 존재하지 않습니다");
                throw new NonExistsPillIndexException();
            }

            List<PillDTO> pillDTOs = new ArrayList<>();

            for(Pill pill : pillList) {
                PillDTO pillDTO = EntityConverter.toPillDTO(pill);
                pillDTOs.add(pillDTO);
            }

            LOGGER.info(".searchPillByAttributesOfPill 약 리스트 조회 성공 {}", pillDTOs);

            return pillDTOs;
        }
        catch (NonExistsPillIndexException ignore){
            throw new NonExistsPillIndexException();
        }
        catch (Exception ignore){
            throw new TransactionFailedException();
        }
    }
}
