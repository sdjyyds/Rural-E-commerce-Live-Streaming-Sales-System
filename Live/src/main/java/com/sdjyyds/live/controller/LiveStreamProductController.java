package com.sdjyyds.live.controller;

import com.sdjyyds.live.entity.LiveStreamProduct;
import com.sdjyyds.live.service.LiveStreamProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/live/stream-product")
public class LiveStreamProductController {
    @Autowired
    private LiveStreamProductService service;

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody LiveStreamProduct product) {
        service.addProductToStream(product);
        return ResponseEntity.ok("商品已关联");
    }

    @GetMapping("/stream/{streamId}")
    public ResponseEntity<List<LiveStreamProduct>> list(@PathVariable Long streamId) {
        return ResponseEntity.ok(service.getProductsForStream(streamId));
    }
}

