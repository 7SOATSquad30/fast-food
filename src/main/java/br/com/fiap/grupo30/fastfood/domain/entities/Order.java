package br.com.fiap.grupo30.fastfood.domain.entities;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CompositeDomainValidationException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class Order {

    private Long id;
    private OrderStatus status;
    private Customer customer;
    private Collection<OrderItem> items = new LinkedList<>();
    private Double totalPrice = 0.0;

    public Order() {
        this.status = OrderStatus.DRAFT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                        () -> this.items.add(new OrderItem(this, product, quantity)));

        this.recalculateTotalPrice();
    }

    public void removeProduct(Product product) {
        this.items.removeIf(orderItem -> orderItem.getProduct().equals(product));
        this.recalculateTotalPrice();
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void recalculateTotalPrice() {
        this.totalPrice = this.items.stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public void validate() {
        var errors = new LinkedList<String>();

        if (this.status == OrderStatus.SUBMITTED && !this.hasProducts()) {
            errors.add("Cannot submit order without products");
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
}
