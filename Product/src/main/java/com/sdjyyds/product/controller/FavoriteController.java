package com.sdjyyds.product.controller;

import com.sdjyyds.product.entity.Product;
import com.sdjyyds.product.service.FavoriteService;
import com.sdjyyds.product.util.SnowflakeIdGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * 收藏商品控制器，提供收藏、取消收藏和获取收藏列表的API接口
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/favorites")
@AllArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final SnowflakeIdGenerator idGenerator;
    /**
     * 将指定商品加入用户收藏
     * @param productId 商品ID
     * @param request   HTTP 请求对象
     * @return 响应结果，表示收藏成功
     */
    @PostMapping("/add")
    public ResponseEntity<?> addFavorite(@RequestParam Long productId, HttpServletRequest request) {
        Long userId = Long.parseLong(getCookieValue(request, "userId"));
        favoriteService.addFavorite(userId, productId);
        return ResponseEntity.ok("收藏成功");
    }

    /**
     * 将指定商品从用户收藏中移除
     * @param productId 商品ID
     * @param request   HTTP 请求对象
     * @return 响应结果，表示取消收藏成功
     */
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFavorite(@RequestParam Long productId, HttpServletRequest request) {
        Long userId = Long.parseLong(getCookieValue(request, "userId"));
        favoriteService.removeFavorite(userId, productId);
        return ResponseEntity.ok("取消收藏成功");
    }

    /**
     * 获取用户的所有收藏商品
     *
     * @param request HTTP 请求对象
     * @return 响应结果，包含用户收藏的商品列表
     */
    @GetMapping("/list")
    public ResponseEntity<List<Product>> listFavorites(HttpServletRequest request) {
        Long userId = Long.parseLong(getCookieValue(request, "userId"));
        return ResponseEntity.ok(favoriteService.getFavorites(userId));
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
