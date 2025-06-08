package com.sdjyyds.user.service;

import com.sdjyyds.user.dto.AddressDTO;
import com.sdjyyds.user.dto.AddressVO;
import com.sdjyyds.user.entity.Address;

import java.util.List;
/**
 * 地址服务接口
 *
 * <p>该接口定义了用户地址信息相关的业务操作方法，包括新增、查询、更新、删除和设置默认地址。</p>
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface AddressService {

    /**
     * 新增用户地址
     *
     * <p>根据传入的地址数据传输对象（AddressDTO）创建一个新的用户地址记录。</p>
     *
     * @param dto 包含地址信息的数据传输对象
     * @return 返回新增后的地址视图对象（AddressVO）
     */
    AddressVO addAddress(AddressDTO dto);

    /**
     * 获取当前用户的所有地址列表
     *
     * <p>查询并返回当前用户的全部地址信息，封装为地址视图对象列表。</p>
     *
     * @return 用户地址视图对象的列表
     */
    List<AddressVO> getUserAddresses();

    /**
     * 根据地址ID获取地址详情
     *
     * <p>通过指定的地址唯一标识符（id），查询对应的地址详细信息。</p>
     *
     * @param id 地址的唯一标识符
     * @return 返回匹配的地址视图对象（AddressVO）
     */
    AddressVO getAddress(Long id);
    //根据用户的userId获取默认的地址
    Address getDefaultAddress(Long userId);
    /**
     * 更新指定ID的地址信息
     *
     * <p>使用提供的地址数据传输对象（AddressDTO）更新指定ID的地址信息。</p>
     *
     * @param id  需要更新的地址唯一标识符
     * @param dto 包含新地址信息的数据传输对象
     * @return 返回更新后的地址视图对象（AddressVO）
     */
    AddressVO updateAddress(Long id, AddressDTO dto);

    /**
     * 删除指定ID的地址
     *
     * <p>根据提供的地址唯一标识符（id）从系统中移除对应的地址记录。</p>
     *
     * @param id 要删除的地址唯一标识符
     */
    void deleteAddress(Long id);

    /**
     * 设置指定地址为默认地址
     *
     * <p>将给定ID的地址标记为用户的默认地址，通常用于下单或展示时自动选择。</p>
     *
     * @param id 要设为默认的地址唯一标识符
     */
    void setDefaultAddress(Long id);
}
