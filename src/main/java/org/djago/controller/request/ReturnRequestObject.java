package org.djago.controller.request;

import java.math.BigDecimal;
import java.util.List;

public class ReturnRequestObject {

	private Long orderId;

//	private LocalDate orderDate;

	private List<ProductCartItem> tableRows;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

//	public LocalDate getOrderDate() {
//		return orderDate;
//	}
//
//	public void setOrderDate(LocalDate orderDate) {
//		this.orderDate = orderDate;
//	}

	public List<ProductCartItem> getTableRows() {
		return tableRows;
	}

	public void setTableRows(List<ProductCartItem> tableRows) {
		this.tableRows = tableRows;
	}

	@Override
	public String toString() {
		return "ReturnRequestObject [orderId=" + orderId +
//				", orderDate=" + orderDate + 
				", tableRows=" + tableRows + "]";
	}

	public BigDecimal getTotal() {

		BigDecimal total = tableRows
				.stream().map(x -> x.getQuantity().multiply(x.getPrice()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

//		total = total.add(total.multiply(taxRate.divide(BigDecimal.valueOf(100))));

		return total;
	}

}