package com.sdjyyds.product.dao;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
import com.sdjyyds.product.entity.Merchant;

import java.util.List;

public interface MerchantDao {
    int deleteByPrimaryKey(Long id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(Long id);

    List<Merchant> selectAll();

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);
}    
