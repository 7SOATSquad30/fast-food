package br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderItemDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Entity
@Table(name = "tb_order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "order_id", nullable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private ProductEntity product;

    @Column(nullable = false)
    @Min(1L)
    private Long quantity = 1L;

    @Column(nullable = false)
    @Min(0)
    private Double totalPrice = 0.0;

    public Long getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
        this.recalculateTotalPrice();
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public ProductEntity getProduct() {
        return this.product;
    }

    private void recalculateTotalPrice() {
        this.totalPrice = this.product.getPrice() * this.quantity;
    }

    public static OrderItemEntity create(OrderEntity order, ProductEntity product, Long quantity) {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.order = order;
        orderItem.product = product;
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    public OrderItemDTO toDTO() {
        OrderItemDTO orderItemDto =
                new OrderItemDTO(this.quantity, this.totalPrice, this.product.toDTO());
        return orderItemDto;
    }
}
