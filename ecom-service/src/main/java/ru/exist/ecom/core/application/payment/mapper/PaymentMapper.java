package ru.exist.ecom.core.application.payment.mapper;

import ru.exist.ecom.core.application.order.mapper.OrderMapper;
import ru.exist.ecom.core.application.payment.PaymentDTO;
import ru.exist.ecom.core.domain.payment.Payment;

public class PaymentMapper {
  public static PaymentDTO toDto(Payment product) {
    return new PaymentDTO(
        product.getId(),
        OrderMapper.toDTO(product.getOrder()),
        product.getStatus(),
        product.getAmount(),
        product.getCreatedAt());
  }

  public static Payment toEntity(PaymentDTO dto) {
    Payment product = new Payment();
    product.setId(dto.id());
    product.setOrder(OrderMapper.toEntity(dto.order()));
    product.setStatus(dto.status());
    product.setAmount(dto.amount());
    product.setCreatedAt(dto.createdAt());

    return product;
  }
}
