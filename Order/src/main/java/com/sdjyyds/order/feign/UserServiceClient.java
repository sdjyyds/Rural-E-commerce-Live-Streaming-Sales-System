package com.sdjyyds.order.feign;

import com.sdjyyds.order.entity.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(name = "${user-service.url}")
public interface UserServiceClient {
    @GetMapping("/api/addresses/default")
    Address getDefaultAddress(@RequestParam("userId") Long userId);
}