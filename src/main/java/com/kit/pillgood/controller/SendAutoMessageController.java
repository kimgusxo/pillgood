package com.kit.pillgood.controller;

import com.kit.pillgood.persistence.dto.AutoMessageDTO;
import com.kit.pillgood.service.SendAutoMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/send-auto-message")
public class SendAutoMessageController {
    private final SendAutoMessageService sendAutoMessageService;

    @Autowired
    public SendAutoMessageController(SendAutoMessageService sendAutoMessageService) {
        this.sendAutoMessageService = sendAutoMessageService;
    }

    @GetMapping("/search/{user-index}")
    public List<AutoMessageDTO> getMessageContentByUserIndex(@PathVariable(name="user-index") Long userIndex) {
        return null;
    }
}
