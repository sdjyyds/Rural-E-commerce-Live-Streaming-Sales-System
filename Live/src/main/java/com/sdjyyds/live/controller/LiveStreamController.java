package com.sdjyyds.live.controller;

import com.sdjyyds.live.entity.LiveStream;
import com.sdjyyds.live.service.LiveStreamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/live/stream")
public class LiveStreamController {
    @Autowired
    private LiveStreamService service;

    @PostMapping("/start")
    public ResponseEntity<String> start(@RequestBody LiveStream stream) {
        service.startLiveStream(stream);
        return ResponseEntity.ok("直播已发起");
    }

    @PostMapping("/end/{id}")
    public ResponseEntity<String> end(@PathVariable Long id) {
        service.endLiveStream(id);
        return ResponseEntity.ok("直播已结束");
    }

    @GetMapping("/{id}")
    public ResponseEntity<LiveStream> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLiveStream(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LiveStream>> list() {
        return ResponseEntity.ok(service.getAllLiveStreams());
    }
}

