package ru.exist.ecom.core.infrastructure.customer;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.exist.ecom.core.domain.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {}
