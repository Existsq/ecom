package ru.exist.ecom.core.application.order;

import java.math.BigDecimal;
import java.util.UUID;

public record DraftItemDTO(UUID productId, Integer quantity, BigDecimal price) {}
