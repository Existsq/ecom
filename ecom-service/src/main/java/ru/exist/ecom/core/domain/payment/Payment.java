package ru.exist.ecom.core.domain.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.exist.ecom.core.domain.enums.PaymentStatus;
import ru.exist.ecom.core.domain.order.Order;

@Entity
@Getter
@Setter
@Table(name = "payments")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal amount;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}
