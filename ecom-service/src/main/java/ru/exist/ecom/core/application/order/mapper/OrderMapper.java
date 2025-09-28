package ru.exist.ecom.core.application.order.mapper;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import ru.exist.ecom.core.application.customer.mapper.CustomerMapper;
import ru.exist.ecom.core.application.order.OrderDTO;
import ru.exist.ecom.core.application.order.OrderItemDTO;
import ru.exist.ecom.core.domain.order.Order;
import ru.exist.ecom.core.domain.order.OrderItem;
import ru.exist.ecom.core.domain.product.Product;

public class OrderMapper {

  public static OrderDTO toDTO(Order order) {
    List<OrderItemDTO> items =
        order.getItems().stream().map(OrderItemMapper::toDTO).collect(Collectors.toList());

    return new OrderDTO(
        order.getId(),
        CustomerMapper.toDto(order.getCustomer()),
        order.getStatus(),
        order.getTotal(),
        order.getCreatedAt(),
        order.getIdempotencyKey(),
        items);
  }

  public static Order toEntity(OrderDTO dto, Map<UUID, Product> productMap) {
    Order order = new Order();
    order.setId(dto.id());
    order.setCustomer(CustomerMapper.toEntity(dto.customer()));
    order.setStatus(dto.status());
    order.setTotal(dto.total());

    List<OrderItem> items =
        dto.items().stream()
            .map(
                it -> {
                  Product product = productMap.get(it.productId());
                  return OrderItemMapper.toEntity(it, order, product);
                })
            .collect(Collectors.toList());

    order.setItems(items);

    return order;
  }
}
