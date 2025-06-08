package com.sdjyyds.live.service;

import com.sdjyyds.live.mapper.MessageMapper;
import com.sdjyyds.live.entity.Message;
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
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private SnowflakeIdGenerator idGenerator;
    @Override
    public void sendMessage(Message msg) {
        msg.setSentTime(new Date());
        msg.setId(idGenerator.nextId());
        messageMapper.insert(msg);
    }

    @Override
    public List<Message> getMessages(Long streamId) {
        return messageMapper.findByStreamId(streamId);
    }
}
