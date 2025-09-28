package ru.exist.ecom.core.application.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import ru.exist.ecom.core.application.order.OrderDTO;
import ru.exist.ecom.core.domain.enums.PaymentStatus;

public record PaymentDTO(
    UUID id, OrderDTO order, PaymentStatus status, BigDecimal amount, LocalDateTime createdAt) {}
