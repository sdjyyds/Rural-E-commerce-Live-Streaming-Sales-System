package com.sdjyyds.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdjyyds.order.dto.OrderPaymentDetailDTO;
import com.sdjyyds.order.entity.*;
import com.sdjyyds.order.feign.ProductServiceClient;
import com.sdjyyds.order.feign.UserServiceClient;
import com.sdjyyds.order.service.OrderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 订单控制器类，处理与订单相关的 HTTP 请求。
 *
 * <p>该类是一个 Spring REST 控制器，负责接收并处理前端发送的订单相关操作，
 * 包括创建订单、查询订单详情、支付、退款、发货以及添加订单项等功能。</p>
 *
 * <p>所有接口路径均以 "/orders" 为前缀。</p>
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;
    /**
     * 创建新订单。
     *
     * <p>接收一个包含订单信息的 JSON 对象，并调用服务层创建订单。</p>
     *
     * @param orderPaymentDetailDTO 包含订单信息的请求体对象
     * @return 返回创建成功的订单 ID
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createOrder(@RequestBody OrderPaymentDetailDTO orderPaymentDetailDTO,
                                            HttpServletRequest request) {
        Long userId = Long.parseLong(getCookieValue(request, "userId"));
        orderPaymentDetailDTO.setUserId(userId);
        log.info("获取用户ID成功！userId: "+userId);
        log.info("开始创建订单！"+orderPaymentDetailDTO);
        //对商品进行操作，更新商品的库存和销量
        // ==== 调用 product-service 修改库存和销量 ====
        Product product = new Product();
        product.setId(orderPaymentDetailDTO.getProductId());
        product.setSales(orderPaymentDetailDTO.getSales()+orderPaymentDetailDTO.getQuantity()); // 增加销量
        product.setStock(orderPaymentDetailDTO.getStock()-orderPaymentDetailDTO.getQuantity()); // 减少库存（负数）

        // 调用远程服务更新商品库存和销量
        try {
            productServiceClient.updateProduct(product);
            log.info("调用商品服务成功，更新库存和销量！");
        } catch (Exception e) {
            log.error("调用商品服务失败，无法更新库存和销量！", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // 调用 user-service 获取默认地址
        Address address = userServiceClient.getDefaultAddress(userId);
        if (address == null) {
            log.error("用户没有默认地址！");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        // 3. 将地址序列化为 JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String addressJson = objectMapper.writeValueAsString(address);
            orderPaymentDetailDTO.setAddress(addressJson);
        } catch (JsonProcessingException e) {
            log.error("序列化地址失败！");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        log.info("获取地址成功！address: "+address);
        // 4. 创建订单
        Long orderId = orderService.createOrder(orderPaymentDetailDTO);
        log.info("创建订单成功！orderId: "+orderId);
        return ResponseEntity.ok(orderId);
    }

    /**
     * 根据订单 ID 查询订单详细信息。
     *
     * <p>通过 URL 路径中的订单 ID 获取对应的订单数据。</p>
     *
     * @param id 需要查询的订单 ID
     * @return 返回完整的订单对象
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    /**
     * 处理订单支付逻辑。
     *
     * <p>接收一个包含支付信息的 JSON 对象，执行支付流程。</p>
     *
     * @param payment 包含支付信息的对象
     * @return 返回支付结果状态信息（如“支付成功”）
     */
    @PostMapping("/pay")
    public ResponseEntity<String> payOrder(@RequestBody Payment payment) {
        return ResponseEntity.ok(orderService.processPayment(payment));
    }

    /**
     * 提交退款申请。
     *
     * <p>接收一个包含退款信息的 JSON 对象，执行退款流程。</p>
     *
     * @param refund 包含退款信息的对象
     * @return 返回退款申请结果状态信息（如“退款已提交”）
     */
    @PostMapping("/refund")
    public ResponseEntity<String> applyRefund(@RequestBody Refund refund) {
        return ResponseEntity.ok(orderService.applyRefund(refund));
    }

    /**
     * 执行发货操作。
     *
     * <p>根据订单 ID 更新订单状态为“已发货”。</p>
     *
     * @param orderId 需要发货的订单 ID
     * @return 返回发货结果状态信息（如“订单已发货”）
     */
    @PostMapping("/ship/{orderId}")
    public ResponseEntity<String> shipOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.shipOrder(orderId));
    }

    /**
     * 向订单中添加商品条目。
     *
     * <p>接收一个包含订单项信息的 JSON 对象，将其添加到指定订单中。</p>
     *
     * @param item 包含订单项信息的对象
     * @return 返回添加结果状态信息（如“商品已加入订单”）
     */
    @PostMapping("/addItem")
    public ResponseEntity<String> addOrderItem(@RequestBody OrderItem item) {
        return ResponseEntity.ok(orderService.addOrderItem(item));
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
    private String getJwtTokenFromRequest(HttpServletRequest request) {
        // 从请求中获取 JWT Token 的逻辑
        // 例如，从请求头或 Cookie 中获取
        return request.getHeader("Authorization").substring(7);
    }
}
