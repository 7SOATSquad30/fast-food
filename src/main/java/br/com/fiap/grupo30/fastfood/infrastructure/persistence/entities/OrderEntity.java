package br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CompositeDomainValidationException;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
    private Collection<OrderItemEntity> items = new LinkedList<OrderItemEntity>();

    @Transient private Double totalPrice = 0.0;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant deletedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

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

    public void recalculateTotalPrice() {
        this.totalPrice = this.items.stream().mapToDouble(OrderItemEntity::getTotalPrice).sum();
    }

    private Boolean hasProducts() {
        return !this.items.isEmpty();
    }

    public void validate() {
        var errors = new LinkedList<String>();

        if (this.status == OrderStatus.SUBMITTED && !this.hasProducts()) {
            errors.add("Can not submit order without products");
        }

        if (this.status == OrderStatus.PREPARING
                && !PaymentStatus.COLLECTED.equals(this.getPayment().getStatus())) {
            errors.add("Can not start peparing order without collecting payment");
        }

        if (!errors.isEmpty()) {
            throw new CompositeDomainValidationException(errors);
        }
    }

    @PostLoad
    protected void postLoad() {
        recalculateTotalPrice();
    }

    @PrePersist
    protected void prePersist() {
        createdAt = Instant.now();
        this.validate();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = Instant.now();
        this.validate();
    }

    @PreRemove
    protected void preRemove() {
        deletedAt = Instant.now();
    }

    public static OrderEntity create() {
        OrderEntity order = new OrderEntity();
        order.status = OrderStatus.DRAFT;
        order.payment = PaymentEntity.create(order);
        return order;
    }

    public void setPaymentProcessing() {
        this.payment.setStatus(PaymentStatus.PROCESSING);
        this.payment.setAmount(this.getTotalPrice());
    }

    public void setPaymentCollected(Double paymentCollectedAmount) {
        this.payment.setStatus(PaymentStatus.COLLECTED);
        this.payment.setAmount(paymentCollectedAmount);
    }

    public void setPaymentRejected() {
        this.payment.setStatus(PaymentStatus.REJECTED);
        this.payment.setAmount(this.getTotalPrice());
    }

    public OrderDTO toDTO() {
        OrderDTO orderDto =
                new OrderDTO(
                        this.id,
                        this.status,
                        this.items.stream().map(item -> item.toDTO()).toArray(OrderItemDTO[]::new),
                        this.getTotalPrice(),
                        this.customer.toDTO(),
                        this.payment.toDTO());
        return orderDto;
    }
}
