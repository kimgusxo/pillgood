package com.kit.pillgood.service;

import com.kit.pillgood.repository.GroupMemberRepository;
import com.kit.pillgood.repository.PrescriptionRepository;
import com.kit.pillgood.repository.TakePillCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendAutoMessageService {
    private final GroupMemberRepository groupMemberRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final DiseaseService diseaseService;
    private final TakePillCheckRepository takePillCheckRepository;

    @Autowired
    public SendAutoMessageService(GroupMemberRepository groupMemberRepository,
                                  PrescriptionRepository prescriptionRepository,
                                  DiseaseService diseaseService,
                                  TakePillCheckRepository takePillCheckRepository) {
        this.groupMemberRepository = groupMemberRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.diseaseService = diseaseService;
        this.takePillCheckRepository = takePillCheckRepository;
    }

    public List<AutoMessageDTO> searchMessageContent(Long userIndex) {
        // 유저인덱스로 약을 검색함
    }

}
