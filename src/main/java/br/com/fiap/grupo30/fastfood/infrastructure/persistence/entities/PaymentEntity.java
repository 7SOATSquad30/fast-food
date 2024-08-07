package br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "tb_payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "order_id", nullable = false, updatable = true)
    private OrderEntity order;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(nullable = false)
    @Min(0)
    private Double amount;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant deletedAt;

    public PaymentEntity(Long paymentId, PaymentStatus status, Double amount) {
        this.id = paymentId;
        this.status = status;
        this.amount = amount;
    }

    public void setParentRelation(OrderEntity order) {
        this.order = order;
    }

    @PrePersist
    protected void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = Instant.now();
    }

    @PreRemove
    protected void preRemove() {
        deletedAt = Instant.now();
    }

    public Payment toDomainEntity() {
        return new Payment(id, status, amount);
    }
}
