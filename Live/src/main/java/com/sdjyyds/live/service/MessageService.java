package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.Message;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface MessageService {
    void sendMessage(Message msg);
    List<Message> getMessages(Long streamId);
}

