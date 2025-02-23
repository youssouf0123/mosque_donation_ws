package org.djago.controller.request;

import javax.validation.constraints.NotEmpty;

public class OrderRequestObject {

	private Long id;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String phone;

	private String address;

	private ShoppingCart cart;

//	private String customerCity;
//	private String customerCountry;

	public OrderRequestObject() {

	}

	public OrderRequestObject(
			Long id, 
			String firstName, 
			String lastName, 
			String phone,
			String address, 
			ShoppingCart cart// ,
//			String customerCity, 
//			String customerCountry
	) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
		this.cart = cart;
//		this.customerCity = customerCity;
//		this.customerCountry = customerCountry;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

//	public String getCustomerCity() {
//		return customerCity;
//	}
//
//	public void setCustomerCity(String customerCity) {
//		this.customerCity = customerCity;
//	}
//
//	public String getCustomerCountry() {
//		return customerCountry;
//	}
//
//	public void setCustomerCountry(String customerCountry) {
//		this.customerCountry = customerCountry;
//	}

	@Override
	public String toString() {
		return "OrderRequestObject [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone="
				+ phone + ", address=" + address + ", cart=" + cart + "]";
	}
	
}