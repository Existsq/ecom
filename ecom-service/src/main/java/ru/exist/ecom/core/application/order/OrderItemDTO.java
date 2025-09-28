package ru.exist.ecom.core.application.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDTO(UUID productId, Integer quantity, BigDecimal price) {}
