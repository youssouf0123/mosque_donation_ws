package org.djago.controller.request;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShoppingCart {

	Logger logger = LoggerFactory.getLogger(ShoppingCart.class);

	private BigDecimal taxRate;
	private BigDecimal shipping;
	private List<ProductCartItem> items;

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getShipping() {
		return shipping;
	}

	public void setShipping(BigDecimal shipping) {
		this.shipping = shipping;
	}

	public List<ProductCartItem> getItems() {
		return items;
	}

	public void setItems(List<ProductCartItem> items) {
		this.items = items;
	}

	public BigDecimal getTotal() {

		BigDecimal total = items
				.stream()
				.map( x -> x.getQuantity().multiply(x.getPrice()) )
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		total = total.add(total.multiply(taxRate.divide(BigDecimal.valueOf(100))));

		return total;
	}

	@Override
	public String toString() {
		return "ShoppingCart [taxRate=" + taxRate + ", shipping=" + shipping + ", items=" + items + "]";
	}

}