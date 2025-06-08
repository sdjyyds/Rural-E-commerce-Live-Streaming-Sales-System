package com.sdjyyds.live.service;

import com.sdjyyds.live.entity.LiveStreamProduct;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface LiveStreamProductService {
    void addProductToStream(LiveStreamProduct product);
    List<LiveStreamProduct> getProductsForStream(Long streamId);
}

