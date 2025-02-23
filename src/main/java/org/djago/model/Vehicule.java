package org.djago.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "vehicule")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Vehicule implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicule_id")
	private Long id;

	@Column
	private String licensePlate;
	private String make;
	private String model;
	private String vin;
	private String bodyType;
	private Long custId;
	private String custFirstName;
	private String custLastName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	//@JsonManagedReference //Bad
	@JsonBackReference //Good
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}
	
	

	public Long getCustId() {
		return this.getCustomer().getId();
	}

	public void setCustId(Long custId) {
		this.custId = this.getCustomer().getId();
	}
	
	public String getCustFirstName() {
		return this.getCustomer().getFirstName();
	}

	public void setCustFirstName(String cusFirstName) {
		this.custFirstName = this.getCustomer().getFirstName();
	}

	public String getCustLastName() {
		return this.getCustomer().getLastName();
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = this.getCustomer().getLastName();
	}

	@Override
	public String toString() {
		return "Vehicule [id=" + id + ", licensePlate=" + licensePlate + ", make=" + make + ", model=" + model
				+ ", vin=" + vin + ", bodyType=" + bodyType + ", customer=" + customer + "]";
	}
	
}