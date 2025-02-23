package org.djago.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column
	private String name;
	private String description;
	private Double price;
	private String image;
	private Integer quantity;

	public Product() {	}

//	public Product(String name, Integer quantity, String image, Double price, Long id) {
//		this.price = price;
//		this.quantity = quantity;
//		this.image = image;
//		this.name = name;
//		this.id = id;
//	}
//
//	public Product(String name, Integer quantity, String image, Double price) {
//		this.price = price;
//		this.quantity = quantity;
//		this.image = image;
//		this.name = name;
//	}
//
//	public Product(String name, Integer quantity, String image, Long id) {
//		this.name = name;
//		this.id = id;
//		this.quantity = quantity;
//		this.image = image;
//	}
//
//	public Product(String name, Integer quantity, String image) {
//		this.name = name;
//		this.image = image;
//		this.quantity = quantity;
//	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + ", image="
				+ image + ", quantity=" + quantity + "]";
	}
	
}