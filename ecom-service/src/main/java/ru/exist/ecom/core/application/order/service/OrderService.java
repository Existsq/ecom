package ru.exist.ecom.core.application.order.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.exist.ecom.core.application.exception.CustomerNotFoundException;
import ru.exist.ecom.core.application.exception.DraftNotFoundException;
import ru.exist.ecom.core.application.exception.EmptyDraftException;
import ru.exist.ecom.core.application.exception.OrderNotFoundException;
import ru.exist.ecom.core.application.order.OrderDTO;
import ru.exist.ecom.core.application.order.mapper.OrderMapper;
import ru.exist.ecom.core.domain.customer.Customer;
import ru.exist.ecom.core.domain.enums.OrderDraftStatus;
import ru.exist.ecom.core.domain.enums.OrderStatus;
import ru.exist.ecom.core.domain.order.*;
import ru.exist.ecom.core.infrastructure.customer.CustomerRepository;
import ru.exist.ecom.core.infrastructure.order.OrderDraftRepository;
import ru.exist.ecom.core.infrastructure.order.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderDraftRepository draftRepository;
  private final CustomerRepository customerRepository;

  @Transactional
  public OrderDTO placeOrder(UUID customerId, UUID idempotencyKey) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));

    // Проверяем идемпотентность: если заказ уже есть, возвращаем его
    return orderRepository
        .findByIdempotencyKey(idempotencyKey)
        .map(OrderMapper::toDTO)
        .orElseGet(
            () -> {
              // Получаем активный драфт
              OrderDraft draft =
                  draftRepository
                      .findByCustomerAndStatus(customer, OrderDraftStatus.ACTIVE)
                      .orElseThrow(
                          () ->
                              new DraftNotFoundException(
                                  "No active draft for customer: " + customerId));

              if (draft.getItems().isEmpty()) {
                throw new EmptyDraftException("Draft is empty. Cannot place order.");
              }

              // Создаем новый заказ
              Order order = new Order();
              order.setCustomer(customer);
              order.setStatus(OrderStatus.NEW);
              order.setTotal(draft.getTotal());
              order.setIdempotencyKey(idempotencyKey);

              // Преобразуем items драфта в OrderItem
              List<OrderItem> items =
                  draft.getItems().stream()
                      .map(
                          draftItem -> {
                            OrderItem oi = new OrderItem();
                            oi.setOrder(order);
                            oi.setProduct(draftItem.getProduct());
                            oi.setQuantity(draftItem.getQuantity());
                            oi.setPrice(draftItem.getPrice());
                            oi.setId(
                                new OrderItemId(order.getId(), draftItem.getProduct().getId()));
                            return oi;
                          })
                      .collect(Collectors.toList());

              order.setItems(items);

              // Сохраняем заказ с items
              Order savedOrder = orderRepository.save(order);

              // Мягкое удаление драфта
              draft.setStatus(OrderDraftStatus.PLACED);
              draftRepository.save(draft);

              return OrderMapper.toDTO(savedOrder);
            });
  }

  public OrderDTO getOrder(UUID orderId) {
    return orderRepository
        .findById(orderId)
        .map(OrderMapper::toDTO)
        .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
  }
}
