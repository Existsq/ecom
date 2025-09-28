package ru.exist.ecom.core.domain.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import ru.exist.ecom.core.domain.customer.Customer;
import ru.exist.ecom.core.domain.enums.OrderDraftStatus;

@Entity
@Getter
@Setter
@Table(name = "order_drafts")
public class OrderDraft {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderDraftStatus status = OrderDraftStatus.ACTIVE;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private Customer customer;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "orderDraft", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DraftItem> items;

  @Column(nullable = false, precision = 19, scale = 2)
  private BigDecimal total;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}
