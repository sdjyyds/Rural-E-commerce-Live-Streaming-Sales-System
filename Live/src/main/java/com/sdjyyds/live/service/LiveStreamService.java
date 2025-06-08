package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.LiveStream;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface LiveStreamService {
    void startLiveStream(LiveStream stream);
    void endLiveStream(Long id);
    LiveStream getLiveStream(Long id);
    List<LiveStream> getAllLiveStreams();
}
