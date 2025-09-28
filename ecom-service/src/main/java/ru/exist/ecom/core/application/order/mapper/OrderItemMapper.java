package ru.exist.ecom.core.application.order.mapper;

import ru.exist.ecom.core.application.order.OrderItemDTO;
import ru.exist.ecom.core.domain.order.Order;
import ru.exist.ecom.core.domain.order.OrderItem;
import ru.exist.ecom.core.domain.order.OrderItemId;
import ru.exist.ecom.core.domain.product.Product;

public class OrderItemMapper {

  public static OrderItemDTO toDTO(OrderItem item) {
    return new OrderItemDTO(item.getProduct().getId(), item.getQuantity(), item.getPrice());
  }

  public static OrderItem toEntity(OrderItemDTO dto, Order order, Product product) {
    OrderItem item = new OrderItem();

    OrderItemId id = new OrderItemId(order.getId(), product.getId());
    item.setId(id);

    item.setOrder(order);
    item.setProduct(product);
    item.setQuantity(dto.quantity());
    item.setPrice(dto.price());

    return item;
  }
}
