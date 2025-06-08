package com.sdjyyds.live.controller;
import com.sdjyyds.live.entity.Streamer;
import com.sdjyyds.live.service.StreamerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */


@RestController
@RequestMapping("/streamer")
public class StreamerController {

    @Autowired
    private StreamerService streamerService;

    @DeleteMapping("/{id}")
    public int deleteByPrimaryKey(@PathVariable Long id) {
        return streamerService.deleteByPrimaryKey(id);
    }

    @PostMapping
    public int insert(@RequestBody Streamer record) {
        return streamerService.insert(record);
    }

    @PostMapping("/selective")
    public int insertSelective(@RequestBody Streamer record) {
        return streamerService.insertSelective(record);
    }

    @GetMapping("/{id}")
    public Streamer selectByPrimaryKey(@PathVariable Long id) {
        return streamerService.selectByPrimaryKey(id);
    }

    @GetMapping
    public List<Streamer> selectAll() {
        return streamerService.selectAll();
    }

    @PutMapping
    public int updateByPrimaryKeySelective(@RequestBody Streamer record) {
        return streamerService.updateByPrimaryKeySelective(record);
    }

    @PutMapping("/full")
    public int updateByPrimaryKey(@RequestBody Streamer record) {
        return streamerService.updateByPrimaryKey(record);
    }
}    
