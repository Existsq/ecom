package ru.exist.ecom.core.application.payment.service;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.exist.ecom.core.application.exception.OrderNotFoundException;
import ru.exist.ecom.core.application.exception.PaymentNotFoundException;
import ru.exist.ecom.core.application.payment.PaymentDTO;
import ru.exist.ecom.core.application.payment.mapper.PaymentMapper;
import ru.exist.ecom.core.domain.enums.OrderStatus;
import ru.exist.ecom.core.domain.enums.PaymentStatus;
import ru.exist.ecom.core.domain.order.Order;
import ru.exist.ecom.core.domain.payment.Payment;
import ru.exist.ecom.core.infrastructure.order.OrderRepository;
import ru.exist.ecom.core.infrastructure.payment.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final OrderRepository orderRepository;

  @Transactional
  public PaymentDTO createPayment(UUID orderId, BigDecimal amount) {

    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found"));

    Payment payment = new Payment();
    payment.setOrder(order);
    payment.setAmount(amount);
    payment.setStatus(PaymentStatus.PENDING);


    return PaymentMapper.toDto(paymentRepository.save(payment));
  }

  @Transactional
  public PaymentDTO completePayment(UUID paymentId) {
    Payment payment =
        paymentRepository
            .findById(paymentId)
            .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

    payment.setStatus(PaymentStatus.COMPLETED);
    paymentRepository.save(payment);

    Order order = payment.getOrder();
    order.setStatus(OrderStatus.PAID);
    orderRepository.save(order);

    return PaymentMapper.toDto(payment);
  }
}
