package com.sdjyyds.live.controller;

import com.sdjyyds.live.service.MessageService;
import com.sdjyyds.live.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/live/message")
public class MessageController {
    @Autowired
    private MessageService service;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody Message msg) {
        msg.setSentTime(new Date());
        service.sendMessage(msg);
        return ResponseEntity.ok("消息已发送");
    }

    @GetMapping("/stream/{streamId}")
    public ResponseEntity<List<Message>> get(@PathVariable Long streamId) {
        return ResponseEntity.ok(service.getMessages(streamId));
    }
}

