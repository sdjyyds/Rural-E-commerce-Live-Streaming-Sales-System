package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.LiveStream;
import com.sdjyyds.live.mapper.LiveStreamMapper;
import com.sdjyyds.live.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class LiveStreamServiceImpl implements LiveStreamService {
    @Autowired
    private LiveStreamMapper mapper;
    @Autowired
    private SnowflakeIdGenerator idGenerator;
    @Override
    public void startLiveStream(LiveStream stream) {
        stream.setStatus("LIVE");
        stream.setId(idGenerator.nextId());
        mapper.insert(stream);
    }

    @Override
    public void endLiveStream(Long id) {
        LiveStream stream = mapper.selectById(id);
        if (stream != null) {
            stream.setStatus("ENDED");
            stream.setEndTime(new Date());
            mapper.update(stream);
        }
    }

    @Override
    public LiveStream getLiveStream(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public List<LiveStream> getAllLiveStreams() {
        return mapper.selectAll();
    }
}

