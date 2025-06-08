package com.sdjyyds.user.controller;

import com.sdjyyds.user.dto.AddressDTO;
import com.sdjyyds.user.dto.AddressVO;
import com.sdjyyds.user.entity.Address;
import com.sdjyyds.user.service.AddressService;
import com.sdjyyds.user.util.ResponseResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
// 控制器类，处理与地址相关的请求
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    // 添加地址的API接口
    @PostMapping
    public ResponseResult<AddressVO> addAddress(@RequestBody @Valid AddressDTO dto) {
        return ResponseResult.success(addressService.addAddress(dto));
    }
    //根据用户的id获取默认地址。
    @GetMapping("/default")
    public Address getDefaultAddress(Long userId) {
        return addressService.getDefaultAddress(userId);
    }
    // 获取用户所有地址列表的API接口
    @GetMapping
    public ResponseResult<List<AddressVO>> getUserAddresses() {
        return ResponseResult.success(addressService.getUserAddresses());
    }

    // 根据ID获取地址详情的API接口
    @GetMapping("/{id}")
    public ResponseResult<AddressVO> getAddress(@PathVariable Long id) {
        return ResponseResult.success(addressService.getAddress(id));
    }

    // 更新指定ID地址信息的API接口
    @PutMapping("/{id}")
    public ResponseResult<AddressVO> updateAddress(
            @PathVariable Long id,
            @RequestBody @Valid AddressDTO dto) {
        return ResponseResult.success(addressService.updateAddress(id, dto));
    }

    // 删除指定ID地址的API接口
    @DeleteMapping("/{id}")
    public ResponseResult<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseResult.success();
    }

    // 设置指定ID地址为默认地址的API接口
    @PutMapping("/{id}/default")
    public ResponseResult<Void> setDefaultAddress(@PathVariable Long id) {
        addressService.setDefaultAddress(id);
        return ResponseResult.success();
    }

}
