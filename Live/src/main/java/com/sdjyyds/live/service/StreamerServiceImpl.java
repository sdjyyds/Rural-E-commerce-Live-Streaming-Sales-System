package com.sdjyyds.live.service;
import com.sdjyyds.live.entity.Streamer;
import com.sdjyyds.live.mapper.StreamerMapper;
import com.sdjyyds.live.util.SnowflakeIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */

@Service
@Slf4j
public class StreamerServiceImpl implements StreamerService {
    private SnowflakeIdGenerator idGenerator;
    @Autowired
    private StreamerMapper streamerMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return streamerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Streamer record) {
        record.setId(idGenerator.nextId());
        log.info("添加主播：{}", record);
        return streamerMapper.insert(record);
    }
    @Override
    public int insertSelective(Streamer record) {
        record.setId(idGenerator.nextId());
        log.info("添加主播：{}", record);
        return streamerMapper.insertSelective(record);
    }

    @Override
    public Streamer selectByPrimaryKey(Long id) {
        log.info("查询主播：{}", id);
        return streamerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Streamer> selectAll() {
        log.info("查询所有主播");
        return streamerMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(Streamer record) {
        log.info("更新主播：{}", record);
        return streamerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Streamer record) {
        log.info("更新主播：{}", record);
        return streamerMapper.updateByPrimaryKey(record);
    }
}    
