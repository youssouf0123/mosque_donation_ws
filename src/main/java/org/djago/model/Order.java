package org.djago.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.djago.controller.request.OrderRequestObject;
import org.djago.util.OrderBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "orders")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Order implements Serializable {

	private static final long serialVersionUID = 100L;

	public enum OrderStatus {
		PENDING, CONFIRMED
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "cust_first_name")
	private String customerFirstName;

	@NotNull
	@Column(name = "cust_last_name")
	private String customerLastName;

	@Column(name = "cust_phone")
	private String customerPhoneNumber;

	@Column(name = "cust_address")
	private String customerAddress;

	@Column(name = "cust_city")
	private String customerCity;

	@Column(name = "cust_country")
	private String customerCountry;

	@NotNull
	private Double amount;
	
//	@NotNull
//	@Column(name = "invoice_docx")
    @Lob
    @Column(name = "invoice_docx", columnDefinition="BLOB")
	private byte[] invoiceDOCX;

//	@NotNull
//	@Column(name = "invoice_pdf")
    @Lob
    @Column(name = "invoice_pdf", columnDefinition="BLOB")
	private byte[] invoicePDF;
	
	@NotNull
	@Column(name = "order_dt")
	private LocalDate orderDate;

	@Column
	private OrderStatus status;

//	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Return> returns;
	
	public Order() {

	}

	public Order(OrderRequestObject order) {
		this.id = order.getId();
		this.customerFirstName = order.getFirstName();
		this.customerLastName = order.getLastName();
		this.customerPhoneNumber = order.getPhone();
		this.customerAddress = order.getAddress();
		this.amount = order.getCart().getTotal().doubleValue();
		this.orderDate = LocalDate.now();
		this.status = OrderStatus.PENDING;
	}
	
	public Order(
			Long id, 
			String customerFirstName, 
			String customerLastName, 
			String customerPhoneNumber,
			String customerAddress, 
			String customerCity, 
			String customerCountry, 
			Double amount,
			byte[] invoiceDOCX,
			byte[] invoicePDF,
			LocalDate orderDate,
			OrderStatus status
	) 
	{
		super();
		this.id = id;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerPhoneNumber = customerPhoneNumber;
		this.customerAddress = customerAddress;
		this.customerCity = customerCity;
		this.customerCountry = customerCountry;
		this.amount = amount;
		this.invoiceDOCX = invoiceDOCX;
		this.invoicePDF = invoicePDF;
		this.orderDate = orderDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public byte[] getInvoiceDOCX() {
		return invoiceDOCX;
	}

	public void setInvoiceDOCX(byte[] invoiceDOCX) {
		this.invoiceDOCX = invoiceDOCX;
	}

	public byte[] getInvoicePDF() {
		return invoicePDF;
	}

	public void setInvoicePDF(byte[] invoicePDF) {
		this.invoicePDF = invoicePDF;
	}
	
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public Set<Return> getReturns() {
		return returns;
	}

	public void setReturns(Set<Return> returns) {
		this.returns = returns;
	}
	
	public static OrderBuilder builder() {
		return new OrderBuilder();
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", customerFirstName=" + customerFirstName + ", customerLastName=" + customerLastName
				+ ", customerPhoneNumber=" + customerPhoneNumber + ", customerAddress=" + customerAddress
				+ ", customerCity=" + customerCity + ", customerCountry=" + customerCountry + ", amount=" + amount
				+ ", orderDate=" + orderDate + ", status=" + status + "]";
	}

}