package br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.dto.OrderItemDTO;
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

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderItemEntity> items;

    @Transient private Double totalPrice;

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
                existingItem -> existingItem.setQuantity(existingItem.getQuantity() + quantity),
                () -> this.items.add(OrderItemEntity.create(product, quantity)));

        this.recalculateTotalPrice();
    }

    public void removeProduct(ProductEntity product) {
        this.items.stream()
            .filter(orderItem -> orderItem.getProduct().equals(product))
            .forEach(existingItem -> this.items.remove(existingItem));

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

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @PostLoad
    private void postLoad() {
        recalculateTotalPrice();
    }

    @PrePersist
    private void prePersist() {
        createdAt = Instant.now();
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = Instant.now();
    }

    @PreRemove
    private void preRemove() {
        deletedAt = Instant.now();
    }

    public static OrderEntity create() {
        OrderEntity order = new OrderEntity();
        order.status = OrderStatus.DRAFT;
        return order;
    }

    public OrderDTO toDTO() {
        OrderDTO orderDto = new OrderDTO(
            this.id,
            this.status,
            this.items.stream().map(item -> item.toDTO()).toArray(OrderItemDTO[]::new),
            this.getTotalPrice()
        );
        return orderDto;
    }
}
