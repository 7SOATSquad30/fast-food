package br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities;

import br.com.fiap.grupo30.fastfood.application.dto.OrderItemDTO;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    @Min(0)
    private Long quantity;

    @Column(nullable = false)
    @Min(0)
    private Double totalPrice;

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

    public static OrderItemEntity create(ProductEntity product, Long quantity) {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.product = product;
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    public OrderItemDTO toDTO() {
        OrderItemDTO orderItemDto = new OrderItemDTO(
            this.quantity,
            this.totalPrice,
            this.product.toDTO()
        );
        return orderItemDto;
    }
}
