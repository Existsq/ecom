package ru.exist.ecom.core.application.order.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.exist.ecom.core.application.exception.CustomerNotFoundException;
import ru.exist.ecom.core.application.exception.DraftNotFoundException;
import ru.exist.ecom.core.application.exception.ProductNotFoundException;
import ru.exist.ecom.core.application.order.OrderDraftDTO;
import ru.exist.ecom.core.application.order.mapper.OrderDraftMapper;
import ru.exist.ecom.core.domain.customer.Customer;
import ru.exist.ecom.core.domain.order.DraftItem;
import ru.exist.ecom.core.domain.order.OrderDraft;
import ru.exist.ecom.core.domain.product.Product;
import ru.exist.ecom.core.infrastructure.customer.CustomerRepository;
import ru.exist.ecom.core.infrastructure.order.OrderDraftRepository;
import ru.exist.ecom.core.infrastructure.product.ProductRepository;

@Service
@AllArgsConstructor
public class DraftService {

  private final OrderDraftRepository draftRepository;
  private final ProductRepository productRepository;
  private final CustomerRepository customerRepository;

  @Transactional
  public OrderDraftDTO addItem(UUID customerId, UUID productId, int quantity) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));

    OrderDraft draft =
        draftRepository
            .findByCustomer(customer)
            .orElseGet(
                () -> {
                  OrderDraft d = new OrderDraft();
                  d.setCustomer(customer);
                  return draftRepository.save(d);
                });

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

    draft.getItems().stream()
        .filter(i -> i.getProduct().getId().equals(productId))
        .findFirst()
        .ifPresentOrElse(
            i -> i.setQuantity(i.getQuantity() + quantity),
            () -> {
              DraftItem item = new DraftItem();
              item.setOrderDraft(draft);
              item.setProduct(product);
              item.setQuantity(quantity);
              item.setPrice(product.getPrice());
              draft.getItems().add(item);
            });

    draft.setTotal(calculateTotal(draft));

    draftRepository.save(draft);

    return OrderDraftMapper.toDTO(draft);
  }

  @Transactional
  public OrderDraftDTO removeItem(UUID customerId, UUID productId) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));

    OrderDraft draft =
        draftRepository
            .findByCustomer(customer)
            .orElseThrow(
                () -> new DraftNotFoundException("Draft not found for customer: " + customerId));

    draft.getItems().removeIf(i -> i.getProduct().getId().equals(productId));

    draft.setTotal(calculateTotal(draft));

    draftRepository.save(draft);

    return OrderDraftMapper.toDTO(draft);
  }

  public OrderDraftDTO getDraft(UUID customerId) {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));

    OrderDraft draft =
        draftRepository
            .findByCustomer(customer)
            .orElseThrow(
                () -> new DraftNotFoundException("Draft not found for customer: " + customerId));

    return OrderDraftMapper.toDTO(draft);
  }

  public BigDecimal calculateTotal(OrderDraft draft) {
    BigDecimal total =
        draft.getItems().stream()
            .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return total.setScale(0, RoundingMode.DOWN);
  }
}
