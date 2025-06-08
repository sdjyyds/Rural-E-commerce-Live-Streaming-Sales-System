package com.sdjyyds.live.mapper;

import com.sdjyyds.live.entity.LiveStream;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface LiveStreamMapper {
    int insert(LiveStream stream);
    int update(LiveStream stream);
    int deleteById(Long id);
    LiveStream selectById(Long id);
    List<LiveStream> selectAll();
}
