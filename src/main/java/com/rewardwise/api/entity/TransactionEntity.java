package com.rewardwise.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Entity representing transaction table.
 */
@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "customer_id")
    private String customerId;

    private double amount;

    private LocalDate date;
    
    public TransactionEntity(Object object, String customerId, double amount, LocalDate date) {
		super();
		this.id=id;
		this.customerId=customerId;
		this.amount=amount;
		this.date=date;
	}

	public TransactionEntity() {
		super();
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}

