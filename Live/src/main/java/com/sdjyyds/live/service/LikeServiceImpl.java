package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.Like;
import com.sdjyyds.live.entity.LiveStream;
import com.sdjyyds.live.mapper.LikeMapper;
import com.sdjyyds.live.mapper.LiveStreamMapper;
import com.sdjyyds.live.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private SnowflakeIdGenerator idGenerator;
    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private LiveStreamMapper liveStreamMapper;

    @Override
    public void likeStream(Like like) {
        // 插入点赞记录
        like.setId(idGenerator.nextId());
        likeMapper.insert(like);
        // 同步更新直播间的点赞数量
        LiveStream stream = liveStreamMapper.selectById(like.getStreamId());
        if (stream != null) {
            int currentCount = stream.getLikeCount() != null ? stream.getLikeCount() : 0;
            stream.setLikeCount(currentCount + 1);
            liveStreamMapper.update(stream);
        }
    }

    @Override
    public int getLikeCount(Long streamId) {
        return likeMapper.countByStreamId(streamId);
    }
}
