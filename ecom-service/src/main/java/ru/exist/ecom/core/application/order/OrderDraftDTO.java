package ru.exist.ecom.core.application.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDraftDTO(
        UUID id,
        UUID customerId,
        LocalDateTime createdAt,
        List<DraftItemDTO> items
) {}