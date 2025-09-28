package ru.exist.ecom.core.infrastructure.order;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.exist.ecom.core.domain.order.OrderDraft;

@Repository
public interface OrderDraftRepository extends JpaRepository<OrderDraft, UUID> {}
