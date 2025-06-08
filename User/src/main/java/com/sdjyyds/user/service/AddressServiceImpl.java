package com.sdjyyds.user.service;

import com.sdjyyds.user.dao.AddressDao;
import com.sdjyyds.user.dao.UserDao;
import com.sdjyyds.user.dto.AddressDTO;
import com.sdjyyds.user.dto.AddressVO;
import com.sdjyyds.user.entity.Address;
import com.sdjyyds.user.exception.BusinessException;
import com.sdjyyds.user.util.SecurityUtils;
import com.sdjyyds.user.util.SnowflakeIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 地址服务实现类
 *
 * <p>该类实现了 {@link AddressService} 接口中定义的所有地址相关业务逻辑，
 * 包括新增、查询、更新、删除以及设置默认地址等功能。</p>
 *
 * <p>依赖于以下组件：</p>
 * <ul>
 *   <li>{@link AddressDao}：用于操作地址数据表</li>
 *   <li>{@link UserDao}：用于用户相关数据操作（如校验用户是否存在）</li>
 * </ul>
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;
    private final SnowflakeIdGenerator idGenerator;
    /**
     * 新增用户地址
     *
     * <p>根据传入的地址数据传输对象（AddressDTO）创建一个新的地址记录。
     * 如果标记为默认地址，则会先清除当前用户的其他默认地址。</p>
     *
     * @param dto 包含新地址信息的数据传输对象
     * @return 返回新增后的地址视图对象（AddressVO）
     */
    @Override
    public AddressVO addAddress(AddressDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            addressDao.clearDefaultAddress(userId);
        }
        Address address = new Address();
        address.setId(idGenerator.nextId());
        address.setUserId(userId);
        address.setRecipientName(dto.getRecipientName());
        address.setPhone(dto.getPhone());
        address.setProvince(dto.getProvince());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setDetailAddress(dto.getDetailAddress());
        address.setIsDefault(dto.getIsDefault());
        address.setCreatedAt(new Date());
        address.setUpdatedAt(new Date());
        addressDao.insert(address);
        return convertToVO(address);
    }

    /**
     * 获取当前用户的所有地址列表
     *
     * <p>从数据库中查询当前用户的所有地址，并将每个地址转换为视图对象返回。</p>
     *
     * @return 用户地址视图对象的列表
     */
    @Override
    public List<AddressVO> getUserAddresses() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<AddressVO> collect = addressDao.findByUserId(userId).stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        log.info("getUserAddresses:{}", collect);
        return collect;
    }

    /**
     * 根据地址ID获取地址详情
     *
     * <p>通过指定的地址唯一标识符（id），查询对应的地址详细信息。</p>
     *
     * @param id 地址的唯一标识符
     * @return 返回匹配的地址视图对象（AddressVO）
     * @throws BusinessException 若地址不存在则抛出异常
     */
    @Override
    public AddressVO getAddress(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        Address address = addressDao.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException("地址不存在"));
        return convertToVO(address);
    }

    /**
     * 更新指定ID的地址信息
     *
     * <p>使用提供的地址数据传输对象（AddressDTO）更新指定ID的地址信息。
     * 如果标记为默认地址，则会先清除当前用户的其他默认地址。</p>
     *
     * @param id  需要更新的地址唯一标识符
     * @param dto 包含新地址信息的数据传输对象
     * @return 返回更新后的地址视图对象（AddressVO）
     * @throws BusinessException 若地址不存在则抛出异常
     */
    @Override
    public AddressVO updateAddress(Long id, AddressDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        Address address = addressDao.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException("地址不存在"));

        if (Boolean.TRUE.equals(dto.getIsDefault())) {
            addressDao.clearDefaultAddress(userId);
        }

        address.setRecipientName(dto.getRecipientName());
        address.setPhone(dto.getPhone());
        address.setProvince(dto.getProvince());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setDetailAddress(dto.getDetailAddress());
        address.setIsDefault(dto.getIsDefault());
        address.setUpdatedAt(new Date());

        addressDao.update(address);

        return convertToVO(address);
    }

    /**
     * 删除指定ID的地址
     *
     * <p>根据提供的地址唯一标识符（id）从系统中移除对应的地址记录。</p>
     *
     * @param id 要删除的地址唯一标识符
     * @throws BusinessException 若地址不存在或不属于当前用户则抛出异常
     */
    @Override
    public void deleteAddress(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        log.info("deleteAddress:{}", id);
        if (!addressDao.existsByIdAndUserId(id, userId)) {
            throw new BusinessException("地址不存在");
        }
        addressDao.delete(id);
    }

    /**
     * 设置指定地址为默认地址
     *
     * <p>将给定ID的地址标记为用户的默认地址。在此之前会先清除其他默认地址。</p>
     *
     * @param id 要设为默认的地址唯一标识符
     * @throws BusinessException 若地址不存在或不属于当前用户则抛出异常
     */
    @Override
    public void setDefaultAddress(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        Address address = addressDao.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BusinessException("地址不存在"));

        addressDao.clearDefaultAddress(userId);

        address.setIsDefault(true);
        address.setUpdatedAt(new Date());
        addressDao.update(address);
    }

    //根据userId获取默认的地址
    public Address getDefaultAddress(Long userId) {
        Address address = addressDao.findByUserIdAndIsDefault(userId, true);
        return address;
    }
    /**
     * 将地址实体对象转换为视图对象
     *
     * <p>用于将数据库查询到的地址实体（Address）转换为前端展示用的视图对象（AddressVO）。</p>
     *
     * @param address 数据库中的地址实体
     * @return 对应的地址视图对象
     */
    private AddressVO convertToVO(Address address) {
        AddressVO vo = new AddressVO();
        vo.setId(address.getId().toString());
        vo.setRecipientName(address.getRecipientName());
        vo.setPhone(address.getPhone());
        vo.setProvince(address.getProvince());
        vo.setCity(address.getCity());
        vo.setDistrict(address.getDistrict());
        vo.setDetailAddress(address.getDetailAddress());
        vo.setIsDefault(address.getIsDefault());
        return vo;
    }

}
