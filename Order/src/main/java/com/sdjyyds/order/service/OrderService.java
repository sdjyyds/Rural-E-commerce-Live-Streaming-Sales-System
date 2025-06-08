package com.sdjyyds.order.service;

import com.sdjyyds.order.dto.OrderPaymentDetailDTO;
import com.sdjyyds.order.entity.Order;
import com.sdjyyds.order.entity.OrderItem;
import com.sdjyyds.order.entity.Payment;
import com.sdjyyds.order.entity.Refund;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface OrderService {
    Long createOrder(OrderPaymentDetailDTO orderPaymentDetailDTO);
    Order getOrderById(Long id);
    String processPayment(Payment payment);
    String applyRefund(Refund refund);
    String shipOrder(Long orderId);
    String addOrderItem(OrderItem item);
}
