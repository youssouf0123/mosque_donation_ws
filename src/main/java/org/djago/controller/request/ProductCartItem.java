package org.djago.controller.request;

import java.math.BigDecimal;

public class ProductCartItem {

	private Long id;

	private String name;

	private BigDecimal price;

	private String image;

	private BigDecimal quantity;

	public ProductCartItem() {}

	public ProductCartItem(
			Long id,
			String name,
			BigDecimal price,
			String image,
			BigDecimal quantity
	) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.quantity = quantity;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ProductCartItem [id=" + id + ", name=" + name + ", price=" + price + ", image=" + image + ", quantity="
				+ quantity + "]";
	}
}