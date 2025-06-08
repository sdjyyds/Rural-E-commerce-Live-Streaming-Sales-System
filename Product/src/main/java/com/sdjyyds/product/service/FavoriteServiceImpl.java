package com.sdjyyds.product.service;

import com.sdjyyds.product.dao.FavoriteDao;
import com.sdjyyds.product.dao.ProductDao;
import com.sdjyyds.product.entity.Product;
import com.sdjyyds.product.util.SnowflakeIdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
// FavoriteServiceImpl.java
@Service
@Slf4j
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteDao favoriteMapper;
    private final ProductDao productMapper;
    private final SnowflakeIdGenerator idGenerator;
    /**
     * 将指定商品加入用户收藏
     *
     * @param userId    用户ID
     * @param productId 商品ID
     */
    public void addFavorite(Long userId, Long productId) {
        log.info("将商品加入收藏：userId={}, productId={}", userId, productId);
        favoriteMapper.insert(idGenerator.nextId(),userId, productId);
    }

    /**
     * 将指定商品从用户收藏中移除
     *
     * @param userId    用户ID
     * @param productId 商品ID
     */
    public void removeFavorite(Long userId, Long productId) {
        log.info("将商品从收藏中移除：userId={}, productId={}", userId, productId);
        favoriteMapper.delete(userId, productId);
    }

    /**
     * 获取用户的所有收藏商品
     *
     * @param userId 用户ID
     * @return 收藏的商品列表
     */
    public List<Product> getFavorites(Long userId) {
        log.info("获取用户的收藏商品：userId={}", userId);
        List<Long> productIds = favoriteMapper.findProductIdsByUserId(userId);
        return productMapper.findByIds(productIds);
    }

    @Override
    public boolean isFavorited(Long userId, Long productId) {
        return favoriteMapper.isFavorited(userId, productId);
    }
}