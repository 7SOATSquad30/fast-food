package br.com.fiap.grupo30.fastfood.domain.entities;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.PaymentStatus;
import java.util.Objects;

public class Payment {
    private Long id;
    private PaymentStatus status;
    private Double amount;

    public Long getId() {
        return id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    public void setStatus(PaymentStatus newStatus) {
        this.status = newStatus;
    }

    public void setAmount(Double newAmount) {
        this.amount = newAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
