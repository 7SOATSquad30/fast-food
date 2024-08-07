package br.com.fiap.grupo30.fastfood.domain.entities;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.PaymentStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CompositeDomainValidationException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class Order {

    private Long id;
    private OrderStatus status;
    private Customer customer;
    private Payment payment;
    private Collection<OrderItem> items;
    private Double totalPrice = 0.0;

    public static Order createFor(Customer customer) {
        return new Order(
                null, OrderStatus.DRAFT, customer, Payment.create(), new LinkedList<OrderItem>());
    }

    public Order(
            Long id,
            OrderStatus status,
            Customer customer,
            Payment payment,
            Collection<OrderItem> items) {
        this.id = id;
        this.status = status;
        this.customer = customer;
        this.payment = payment;
        this.items = items;
        recalculateTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Payment getPayment() {
        return payment;
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

    public Collection<OrderItem> getItems() {
        return items;
    }

    public void addProduct(Product product, Long quantity) {
        this.items.stream()
                .filter(orderItem -> orderItem.getProduct().equals(product))
                .findFirst()
                .ifPresentOrElse(
                        existingItem ->
                                existingItem.setQuantity(existingItem.getQuantity() + quantity),
                        () -> this.items.add(new OrderItem(product, quantity)));

        this.recalculateTotalPrice();
    }

    public void removeProduct(Product product) {
        this.items.removeIf(orderItem -> orderItem.getProduct().equals(product));
        this.recalculateTotalPrice();
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    private void recalculateTotalPrice() {
        this.totalPrice = this.items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public void validate() {
        var errors = new LinkedList<String>();

        if (this.status == OrderStatus.SUBMITTED && !this.hasProducts()) {
            errors.add("Cannot submit order without products");
        }

        if (this.status == OrderStatus.PREPARING
                && !PaymentStatus.COLLECTED.equals(this.getPayment().getStatus())) {
            errors.add("Can not start peparing order without collecting payment");
        }

        if (!errors.isEmpty()) {
            throw new CompositeDomainValidationException(errors);
        }
    }

    private boolean hasProducts() {
        return !this.items.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public OrderDTO toDTO() {
        return new OrderDTO(
                id,
                status,
                items.stream().map(item -> item.toDTO()).toArray(OrderItemDTO[]::new),
                totalPrice,
                customer.toDTO(),
                payment.toDTO());
    }

    public OrderEntity toPersistence() {
        return new OrderEntity(
                id,
                status,
                customer.toPersistence(),
                payment.toPersistence(),
                items.stream().map(OrderItem::toPersistence).toList());
    }
}
