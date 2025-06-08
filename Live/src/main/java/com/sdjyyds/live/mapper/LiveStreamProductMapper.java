package com.sdjyyds.live.mapper;

import com.sdjyyds.live.entity.LiveStreamProduct;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface LiveStreamProductMapper {
    int insert(LiveStreamProduct product);
    List<LiveStreamProduct> findByStreamId(Long streamId);
}

