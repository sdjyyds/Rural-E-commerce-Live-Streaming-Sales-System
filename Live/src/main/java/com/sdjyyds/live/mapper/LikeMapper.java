package com.sdjyyds.live.mapper;

import com.sdjyyds.live.entity.Like;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface LikeMapper {
    int insert(Like like);
    int countByStreamId(Long streamId);
}

