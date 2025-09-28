package ru.exist.ecom.core.infrastructure.payment;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.exist.ecom.core.domain.payment.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {}
