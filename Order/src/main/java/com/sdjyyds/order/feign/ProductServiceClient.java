package com.sdjyyds.order.feign;

import com.sdjyyds.order.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@FeignClient(name = "product-service") // nacos中注册的商品服务名
public interface ProductServiceClient {

    @PutMapping("/products/update")
    void updateProduct(@RequestBody Product product);
}

