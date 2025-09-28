package ru.exist.ecom.core.infrastructure.order;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.exist.ecom.core.domain.customer.Customer;
import ru.exist.ecom.core.domain.enums.OrderDraftStatus;
import ru.exist.ecom.core.domain.order.OrderDraft;

@Repository
public interface OrderDraftRepository extends JpaRepository<OrderDraft, UUID> {

  Optional<OrderDraft> findByCustomer(Customer customer);

  Optional<OrderDraft> findByCustomerAndStatus(Customer customer, OrderDraftStatus status);
}
