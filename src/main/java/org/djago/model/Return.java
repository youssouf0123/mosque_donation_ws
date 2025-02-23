package org.djago.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "returns")
public class Return implements Serializable {

	private static final long serialVersionUID = 101L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "amount")
	private Double amount;

	@ManyToOne
//	@ManyToOne(fetch = FetchType.EAGER)	
	@JoinColumn(name = "order_id", nullable = false)
//	@JsonBackReference
	private Order order;

	@NotNull
	@Column(name = "return_dt")
	private LocalDate returnDate;

//	@NotNull
    @Column(name = "return_invoice_docx", columnDefinition="BLOB")	
	private byte[] returnInvoiceDOCX;
	
	//	@NotNull
    @Column(name = "return_invoice_pdf", columnDefinition="BLOB")	
	private byte[] returnInvoicePDF;

	public Return() {
		this.returnDate = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public byte[] getReturnInvoiceDOCX() {
		return returnInvoiceDOCX;
	}

	public void setReturnInvoiceDOCX(byte[] returnInvoiceDOCX) {
		this.returnInvoiceDOCX = returnInvoiceDOCX;
	}

	public byte[] getReturnInvoicePDF() {
		return returnInvoicePDF;
	}

	public void setReturnInvoicePDF(byte[] returnInvoicePDF) {
		this.returnInvoicePDF = returnInvoicePDF;
	}
	
	@Override
	public String toString() {
		return "Return [id=" + id + ", amount=" + amount + ", orderId=" + order.getId() + ", returnDate="
				+ returnDate + "]";
	}

}