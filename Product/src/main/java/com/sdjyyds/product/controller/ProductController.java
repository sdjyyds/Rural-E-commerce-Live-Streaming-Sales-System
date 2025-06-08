package com.sdjyyds.product.controller;

import com.sdjyyds.product.dto.ProductVO;
import com.sdjyyds.product.entity.Product;
import com.sdjyyds.product.service.ProductService;
import com.sdjyyds.product.util.SnowflakeIdGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 商品管理控制器，处理商品相关的增删改查请求
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private SnowflakeIdGenerator idGenerator;
    /**
     * 添加新商品
     * @param product 请求体中的商品信息
     * @return 响应结果：成功消息
     */
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        product.setId(idGenerator.nextId());
        productService.addProduct(product);
        log.info("添加商品：{}", product);
        return ResponseEntity.ok("商品上架成功");
    }

    /**
     * 更新已有商品信息
     * @param product 请求体中的更新商品信息
     * @return 响应结果：成功消息
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        log.info("更新商品：{}", product);
        return ResponseEntity.ok("商品更新成功");
    }

    /**
     * 根据ID删除商品（下架）
     * @param id 要删除的商品ID
     * @return 响应结果：成功消息
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        log.info("删除商品ID：{}", id);
        return ResponseEntity.ok("商品下架成功");
    }

    /**
     * 获取商品列表，支持关键词搜索
     * @param keyword 搜索关键词（可选）
     * @return 响应结果：商品列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<ProductVO>> listProducts(@RequestParam(required = false) String keyword,HttpServletRequest request) {
        Long userId = Long.parseLong(getCookieValue(request, "userId"));
        log.info("获取商品列表，关键词：{}", keyword);
        return ResponseEntity.ok(productService.searchProducts(keyword,  userId));
    }
    @GetMapping("/descriptionKeywordCounts")
    public ResponseEntity<Map<String, Integer>> getProductCountByDescriptionKeywords(
            @RequestParam List<String> keywords) {
        log.info("获取商品描述中包含关键词的个数，关键词：{}", keywords);
        Map<String, Integer> result = productService.getProductCountByKeywordsInDescription(keywords);
        log.info("商品描述中包含关键词的个数结果：{}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * 从 HttpServletRequest 中获取指定名称的 Cookie 值
     *
     * @param request    请求对象
     * @param cookieName 要查找的 Cookie 名称
     * @return Cookie 的值，如果未找到则返回 null
     */
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

