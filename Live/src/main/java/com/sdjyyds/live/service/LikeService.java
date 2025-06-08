package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.Like;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface LikeService {
    void likeStream(Like like);
    int getLikeCount(Long streamId);
}

