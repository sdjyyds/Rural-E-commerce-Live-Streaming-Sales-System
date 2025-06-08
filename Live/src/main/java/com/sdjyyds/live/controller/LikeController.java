package com.sdjyyds.live.controller;

import com.sdjyyds.live.entity.Like;
import com.sdjyyds.live.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/live/like")
public class LikeController {
    @Autowired
    private LikeService service;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Like like) {
        like.setCreatedAt(new Date());
        service.likeStream(like);
        return ResponseEntity.ok("点赞成功");
    }

    @GetMapping("/count/{streamId}")
    public ResponseEntity<Integer> count(@PathVariable Long streamId) {
        return ResponseEntity.ok(service.getLikeCount(streamId));
    }
}

