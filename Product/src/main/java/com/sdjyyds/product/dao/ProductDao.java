package com.sdjyyds.product.dao;

import com.sdjyyds.product.entity.Product;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
/**
 * 商品数据访问层接口，定义了对商品表的基本数据库操作。
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ProductDao {
    /**
     * 插入一个新的商品记录到数据库中。
     *
     * @param product 包含要插入的商品信息的对象
     */
    void insert(Product product);

    /**
     * 更新已存在的商品记录。
     *
     * @param product 包含更新后商品信息的对象
     */
    void update(Product product);

    /**
     * 根据商品ID删除商品记录。
     *
     * @param id 要删除的商品的唯一标识符
     */
    void delete(Long id);

    /**
     * 根据关键词搜索商品，通常用于模糊匹配商品名称或类别。
     *
     * @param keyword 搜索关键字
     * @return 匹配关键字的商品列表
     */
    List<Product> search(@Param("keyword") String keyword);

    /**
     * 根据一组商品ID获取对应的商品记录。
     *
     * @param ids 需要获取的商品ID列表
     * @return 对应ID的商品列表
     */
    List<Product> findByIds(@Param("ids") List<Long> ids);
    List<Map<String, Object>> getProductCountByKeywordsInDescription(@Param("keywords") List<String> keywords);
}
