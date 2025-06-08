package com.sdjyyds.order.service;

import com.sdjyyds.order.dto.OrderPaymentDetailDTO;
import com.sdjyyds.order.entity.Order;
import com.sdjyyds.order.entity.OrderItem;
import com.sdjyyds.order.entity.Payment;
import com.sdjyyds.order.entity.Refund;
import com.sdjyyds.order.mapper.OrderItemMapper;
import com.sdjyyds.order.mapper.OrderMapper;
import com.sdjyyds.order.mapper.PaymentMapper;
import com.sdjyyds.order.mapper.RefundMapper;
import com.sdjyyds.order.util.OrderNumberGenerator;
import com.sdjyyds.order.util.SnowflakeIdGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;
    private final RefundMapper refundMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderNumberGenerator  orderNumberGenerator;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(4, 4);
    @Override
    public Long createOrder(OrderPaymentDetailDTO orderPaymentDetailDTO) {
        Order order = new Order();
        order.setId(idGenerator.nextId());
        order.setOrderNo(orderNumberGenerator.generateOrderNumber());
        order.setUserId(orderPaymentDetailDTO.getUserId());
        order.setOrderTime(new Date());
        order.setStatus("PAID");
        order.setTotalPrice(orderPaymentDetailDTO.getTotalPrice());
        order.setPaymentAmount(orderPaymentDetailDTO.getPaymentAmount());
        order.setAddressJson(orderPaymentDetailDTO.getAddress());
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        orderMapper.insert(order);

        OrderItem orderItem  = new OrderItem();
        orderItem.setId(idGenerator.nextId());
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(orderPaymentDetailDTO.getProductId());
        orderItem.setProductName(orderPaymentDetailDTO.getProductName());
        orderItem.setProductImage(orderPaymentDetailDTO.getProductImage());
        orderItem.setQuantity(orderPaymentDetailDTO.getQuantity());
        orderItem.setUnitPrice(orderPaymentDetailDTO.getUnitPrice());
        orderItem.setActualPrice(orderPaymentDetailDTO.getActualPrice());
        orderItem.setRefundStatus("NONE");
        orderItem.setCreatedAt(new Date());
        orderItem.setUpdatedAt(new Date());
        orderItemMapper.insert(orderItem);

        Payment payment = new Payment();
        payment.setId(idGenerator.nextId());
        payment.setOrderId(order.getId());
        payment.setPaymentNo(orderNumberGenerator.generateOrderNumber());
        payment.setUserId(orderPaymentDetailDTO.getUserId());
        payment.setPaymentTime(new Date());
        payment.setAmount(orderPaymentDetailDTO.getPaymentAmount());
        payment.setMethod("alipay");//假设支付方式为线上
        payment.setStatus("SUCCESS");//支付成功
        payment.setCreatedAt(new Date());
        payment.setUpdatedAt(new Date());
        paymentMapper.insert(payment);
        return order.getId();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public String processPayment(Payment payment) {
        payment.setId(idGenerator.nextId());
        payment.setPaymentTime(new Date());
        payment.setCreatedAt(new Date());
        paymentMapper.insert(payment);
        orderMapper.updateStatus(payment.getOrderId(), "PAID");
        return "Payment processed.";
    }

    @Override
    public String applyRefund(Refund refund) {
        refund.setId(idGenerator.nextId());
        refund.setAppliedAt(new Date());
        refund.setCreatedAt(new Date());
        refundMapper.insert(refund);
        orderMapper.updateStatus(refund.getOrderId(), "REFUND_REQUESTED");
        return "Refund requested.";
    }

    @Override
    public String shipOrder(Long orderId) {
        orderMapper.updateStatus(orderId, "SHIPPED");
        return "Order shipped.";
    }

    @Override
    public String addOrderItem(OrderItem item) {
        item.setId(idGenerator.nextId());
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());
        orderItemMapper.insert(item);
        return "Order item added.";
    }
}
