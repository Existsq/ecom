package ru.exist.ecom.core.infrastructure.product;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.exist.ecom.core.domain.product.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {}
