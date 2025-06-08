package com.sdjyyds.product.service;

import com.sdjyyds.product.dto.ProductVO;
import com.sdjyyds.product.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
// ProductService.java
public interface ProductService {
    /**
     * 添加商品
     * @param product 商品实体
     */
    void addProduct(Product product);

    /**
     * 更新商品信息
     * @param product 商品实体
     */
    void updateProduct(Product product);

    /**
     * 根据商品ID删除商品
     * @param id 商品ID
     */
    void deleteProduct(Long id);

    /**
     * 根据关键词搜索商品
     * @param keyword 搜索关键词
     * @return 商品列表
     */
    List<ProductVO> searchProducts(String keyword,Long userId);
    Map<String, Integer> getProductCountByKeywordsInDescription(List<String> keywords);
}
