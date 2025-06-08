package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.Streamer;

import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface StreamerService {
    int deleteByPrimaryKey(Long id);

    int insert(Streamer record);

    int insertSelective(Streamer record);

    Streamer selectByPrimaryKey(Long id);

    List<Streamer> selectAll();

    int updateByPrimaryKeySelective(Streamer record);

    int updateByPrimaryKey(Streamer record);
}    
