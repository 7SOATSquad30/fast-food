package br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "tb_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToOne(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private PaymentEntity payment;

    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<OrderItemEntity> items;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE", updatable = false)
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant deletedAt;

    public OrderEntity(
            Long orderId,
            OrderStatus status,
            CustomerEntity customer,
            PaymentEntity payment,
            Collection<OrderItemEntity> orderItems) {
        this.id = orderId;
        this.status = status;
        this.customer = customer;
        this.payment = payment;
        this.items = orderItems;

        this.payment.setParentRelation(this);
        for (OrderItemEntity orderItem : this.items) {
            orderItem.setParentRelation(this);
        }
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

    public Order toDomainEntity() {
        return new Order(
                id,
                status,
                customer.toDomainEntity(),
                payment.toDomainEntity(),
                items.stream()
                        .map(OrderItemEntity::toDomainEntity)
                        .collect(Collectors.toCollection(ArrayList::new)));
    }
}
