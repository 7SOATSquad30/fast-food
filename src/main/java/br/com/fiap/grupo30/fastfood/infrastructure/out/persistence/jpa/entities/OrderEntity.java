package br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.dto.OrderItemDTO;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Collection<OrderItemEntity> items = new LinkedList<OrderItemEntity>();

    @Transient private Double totalPrice = 0.0;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant deletedAt;

    public void addProduct(ProductEntity product, Long quantity) {
        this.items.stream()
                .filter(orderItem -> orderItem.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                        existingItem ->
                                existingItem.setQuantity(existingItem.getQuantity() + quantity),
                        () -> this.items.add(OrderItemEntity.create(this, product, quantity)));

        this.recalculateTotalPrice();
    }

    public void removeProduct(ProductEntity product) {
        this.items.removeIf(orderItem -> orderItem.getProduct().equals(product));

        this.recalculateTotalPrice();
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    private void recalculateTotalPrice() {
        this.totalPrice = this.items.stream().mapToDouble(item -> item.getTotalPrice()).sum();
    }

    public Boolean isDraft() {
        return OrderStatus.DRAFT.equals(this.status);
    }

    public Boolean hasProducts() {
        return !this.items.isEmpty();
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @PostLoad
    protected void postLoad() {
        recalculateTotalPrice();
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

    public static OrderEntity create() {
        OrderEntity order = new OrderEntity();
        order.status = OrderStatus.DRAFT;
        return order;
    }

    public OrderDTO toDTO() {
        OrderDTO orderDto =
                new OrderDTO(
                        this.id,
                        this.status,
                        this.items.stream().map(item -> item.toDTO()).toArray(OrderItemDTO[]::new),
                        this.getTotalPrice());
        return orderDto;
    }
}
