package com.sdjyyds.product.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * 收藏数据访问接口，用于管理用户收藏的商品
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface FavoriteDao {
    /**
     * 添加商品到用户的收藏列表
     *
     * @param userId   用户ID
     * @param productId 商品ID
     */
    void insert(@Param("id")Long id,@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 从用户的收藏列表中移除指定商品
     *
     * @param userId   用户ID
     * @param productId 商品ID
     */
    void delete(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 根据用户ID查询其收藏的所有商品ID列表
     *
     * @param userId 用户ID
     * @return 返回用户收藏的商品ID列表
     */
    List<Long> findProductIdsByUserId(@Param("userId") Long userId);

    Boolean isFavorited(@Param("userId") Long userId, @Param("productId") Long productId);
}