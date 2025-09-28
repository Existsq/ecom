package ru.exist.ecom.core.application.order.mapper;

import ru.exist.ecom.core.application.order.DraftItemDTO;
import ru.exist.ecom.core.domain.order.DraftItem;

public class DraftItemMapper {

  public static DraftItemDTO toDTO(DraftItem item) {
    return new DraftItemDTO(item.getProduct().getId(), item.getQuantity(), item.getPrice());
  }
}
