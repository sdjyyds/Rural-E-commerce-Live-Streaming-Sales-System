package com.sdjyyds.product.service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
import com.sdjyyds.product.dao.MerchantDao;
import com.sdjyyds.product.entity.Merchant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantDao merchantMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        log.info("删除商户信息，商户ID：{}", id);
        return merchantMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Merchant record) {
        log.info("添加商户信息：{}", record);
        return merchantMapper.insert(record);
    }

    @Override
    public int insertSelective(Merchant record) {
        log.info("添加商户信息：{}", record);
        return merchantMapper.insertSelective(record);
    }

    @Override
    public Merchant selectByPrimaryKey(Long id) {
        log.info("查询商户信息，商户ID：{}", id);
        return merchantMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Merchant> selectAll() {
        log.info("查询所有商户信息");
        return merchantMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKeySelective(Merchant record) {
        log.info("更新商户信息：{}", record);
        return merchantMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Merchant record) {
        return merchantMapper.updateByPrimaryKey(record);
    }
}
