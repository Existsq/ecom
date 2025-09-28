package ru.exist.ecom.core.domain.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal price;

  @Column(nullable = false)
  private Integer stock = 0;
}
