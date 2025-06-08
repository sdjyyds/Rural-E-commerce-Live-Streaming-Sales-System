package com.sdjyyds.live.mapper;

import com.sdjyyds.live.entity.Message;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface MessageMapper {
    int insert(Message msg);
    List<Message> findByStreamId(Long streamId);
}

