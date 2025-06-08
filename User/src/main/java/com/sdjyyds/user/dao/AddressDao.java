package com.sdjyyds.user.dao;

import com.sdjyyds.user.entity.Address;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.List;
/**
 * 地址数据访问接口，定义了对地址信息的 CRUD 操作及相关查询。
 */
public interface AddressDao {

    /**
     * 插入新地址。
     * @param address 要插入的地址对象
     */
    void insert(Address address);

    /**
     * 更新现有地址信息。
     * @param address 包含更新数据的地址对象
     */
    void update(Address address);

    /**
     * 删除指定 ID 的地址。
     * @param id 要删除的地址的 ID
     */
    void delete(Long id);

    /**
     * 根据地址 ID 和用户 ID 查找地址。
     * @param id 地址 ID
     * @param userId 用户 ID
     * @return 返回包含地址的 Optional 对象，避免空指针异常
     */
    Optional<Address> findByIdAndUserId(@Param("id") Long id,@Param("userId") Long userId);

    /**
     * 根据用户 ID 查找所有地址。
     * @param userId 用户 ID
     * @return 返回用户的所有地址列表
     */
    List<Address> findByUserId(Long userId);

    /**
     * 检查指定 ID 和用户 ID 的地址是否存在。
     * @param id 地址 ID
     * @param userId 用户 ID
     * @return 如果地址存在返回 true，否则返回 false
     */
    boolean existsByIdAndUserId(@Param("id") Long id,@Param("userId") Long userId);

    /**
     * 清除指定用户的默认地址标志。
     * @param userId 用户 ID
     */
    void clearDefaultAddress(Long userId);

    //
    Address findByUserIdAndIsDefault(@Param("userId") Long userId,@Param("isDefault") Boolean isDefault);
}

