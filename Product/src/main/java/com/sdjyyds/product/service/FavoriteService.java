package com.sdjyyds.product.service;

import com.sdjyyds.product.entity.Product;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
import org.springframework.stereotype.Service;


/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public interface FavoriteService {
    /**
     * 将指定商品加入用户收藏
     * @param userId 用户ID
     * @param productId 商品ID
     */
    void addFavorite(Long userId, Long productId);

    /**
     * 将指定商品从用户收藏中移除
     * @param userId 用户ID
     * @param productId 商品ID
     */
    void removeFavorite(Long userId, Long productId);

    /**
     * 获取用户的所有收藏商品
     * @param userId 用户ID
     * @return 收藏的商品列表
     */
    List<Product> getFavorites(Long userId);

    /**
     * 判断用户是否收藏了指定商品
     * @param userId 用户ID
     * @param productId 商品ID
     * @return true表示已收藏，false表示未收藏
     */
    boolean isFavorited(Long userId, Long productId);
}

