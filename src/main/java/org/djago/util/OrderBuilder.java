package org.djago.util;

import java.time.LocalDate;

import org.djago.model.Order;
import org.djago.model.Order.OrderStatus;


public class OrderBuilder {

	private Long id;
	private String customerFirstName;
	private String customerLastName;
	private String customerPhoneNumber;
	private String customerAddress;
	private String customerCity;
	private String customerCountry;
	private Double amount;
	private byte[] invoiceDOCX;
	private byte[] invoicePDF;
	private LocalDate orderDate;
	private OrderStatus status;

	public OrderBuilder() {

	}

	public OrderBuilder id(Long id) {
		this.id = id;
		return this;
	}

	public OrderBuilder customerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
		return this;
	}

	public OrderBuilder customerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
		return this;
	}

	public OrderBuilder customerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
		return this;
	}

	public OrderBuilder customerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
		return this;
	}

	public OrderBuilder customerCity(String customerCity) {
		this.customerCity = customerCity;
		return this;
	}

	public OrderBuilder customerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
		return this;
	}

	public OrderBuilder amount(Double amount) {
		this.amount = amount;
		return this;
	}

	public OrderBuilder invoiceDOCX(byte[] invoiceDOCX) {
		this.invoiceDOCX = invoiceDOCX;
		return this;
	}

	public OrderBuilder invoicePDF(byte[] invoicePDF) {
		this.invoicePDF = invoicePDF;
		return this;
	}
	
	public OrderBuilder orderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
		return this;
	}

	public OrderBuilder status(OrderStatus status) {
		this.status = status;
		return this;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerFirstName=" + customerFirstName + ", customerLastName=" + customerLastName
				+ ", customerPhoneNumber=" + customerPhoneNumber + ", customerAddress=" + customerAddress
				+ ", customerCity=" + customerCity + ", customerCountry=" + customerCountry + ", amount=" + amount + ", orderDate=" + orderDate
				+ ", status=" + status + "]";
	}

	public Order build() {
		return new Order(
				id,
				customerFirstName, 
				customerLastName,
				customerPhoneNumber, 
				customerAddress,
				customerCity,
				customerCountry,
				amount,
				invoiceDOCX,
				invoicePDF,
				orderDate,
				status
		);
	}
}