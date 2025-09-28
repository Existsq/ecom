package ru.exist.ecom.core.application.order.mapper;

import java.util.stream.Collectors;
import ru.exist.ecom.core.application.order.OrderDraftDTO;
import ru.exist.ecom.core.domain.order.OrderDraft;

public class OrderDraftMapper {

  public static OrderDraftDTO toDTO(OrderDraft draft) {
    return new OrderDraftDTO(
        draft.getId(),
        draft.getCustomer().getId(),
        draft.getCreatedAt(),
        draft.getItems().stream().map(DraftItemMapper::toDTO).collect(Collectors.toList()));
  }
}
