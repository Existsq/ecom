package ru.exist.ecom.core.application.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import ru.exist.ecom.core.application.customer.CustomerDTO;
import ru.exist.ecom.core.domain.enums.OrderStatus;

public record OrderDTO(
    UUID id,
    CustomerDTO customer,
    OrderStatus status,
    BigDecimal total,
    LocalDateTime createdAt,
    UUID idempotencyKey,
    List<OrderItemDTO> items) {}
