package com.sdjyyds.product.service;

import com.sdjyyds.product.dao.ProductDao;
import com.sdjyyds.product.dto.ProductVO;
import com.sdjyyds.product.entity.Product;
import com.sdjyyds.product.util.SnowflakeIdGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
// ProductServiceImpl.java
/**
 * 商品服务实现类
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductDao productMapper;
    private final FavoriteService favoriteService;
    // ID生成器，使用雪花算法
    private static final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(2, 2);

    /**
     * 添加商品
     * @param product 商品实体
     */
    public void addProduct(Product product) {
        log.info("添加商品：{}", product);
        product.setId(idGenerator.nextId());
        product.setMerchantId(idGenerator.nextId());
        productMapper.insert(product);
    }

    /**
     * 更新商品信息
     * @param product 商品实体
     */
    public void updateProduct(Product product) {

        log.info("更新商品：{}", product);
        productMapper.update(product);
    }

    /**
     * 根据商品ID删除商品
     * @param id 商品ID
     */
    public void deleteProduct(Long id) {
        log.info("删除商品：{}", id);
        productMapper.delete(id);
    }

    /**
     * 根据关键词搜索商品
     * @param keyword 搜索关键词
     * @return 商品列表
     */
    public List<ProductVO> searchProducts(String keyword, Long userId) {
        log.info("搜索商品：{}", keyword);
        return changeProduct(productMapper.search(keyword),userId);
    }
    private List<ProductVO> changeProduct(List<Product> products, Long userId){
        List<ProductVO> productsVO = new ArrayList<>();
        for(Product product:products)
        {
            ProductVO productVO = ProductVO.tranlate(product);
            //查询是否被收藏
            productVO.setBefavorited(favoriteService.isFavorited(userId, product.getId()));
            productsVO.add(productVO);
        }
        return productsVO;
    }
    @Override
    public Map<String, Integer> getProductCountByKeywordsInDescription(List<String> keywords) {
        List<Map<String, Object>> result = productMapper.getProductCountByKeywordsInDescription(keywords);
        Map<String, Integer> countMap = new HashMap<>();
        for (Map<String, Object> row : result) {
            String keyword = (String) row.get("keyword");
            Integer count = ((Number) row.get("count")).intValue();
            countMap.put(keyword, count);
        }
        return countMap;
    }
}


