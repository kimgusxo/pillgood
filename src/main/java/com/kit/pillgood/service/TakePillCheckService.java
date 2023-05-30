package com.kit.pillgood.service;

import com.kit.pillgood.repository.TakePillCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TakePillCheckService {
    private final TakePillCheckRepository takePillCheckRepository;

    @Autowired
    public TakePillCheckService(TakePillCheckRepository takePillCheckRepository) {
        this.takePillCheckRepository = takePillCheckRepository;
    }
}
