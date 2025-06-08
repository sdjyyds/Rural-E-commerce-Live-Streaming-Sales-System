package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.LiveStreamProduct;
import com.sdjyyds.live.mapper.LiveStreamProductMapper;
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
public class LiveStreamProductServiceImpl implements LiveStreamProductService {

    @Autowired
    private LiveStreamProductMapper streamProductMapper;
    @Autowired
    private  SnowflakeIdGenerator idGenerator;
    @Override
    public void addProductToStream(LiveStreamProduct product) {
        product.setCreatedAt(new Date());
        product.setId(idGenerator.nextId());
        streamProductMapper.insert(product);
    }

    @Override
    public List<LiveStreamProduct> getProductsForStream(Long streamId) {
        return streamProductMapper.findByStreamId(streamId);
    }
}

