/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.djago.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "customer")
public class Customer  implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long id;
	
	@Column
	private String firstName;
	private String lastName;
	private String phoneNum ;
	private EmailAddress emailAddress;


	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL,mappedBy="customer")
	//@LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Vehicule> vehicule;
	
	//@JsonBackReference //Bad
	@JsonManagedReference //Good
	public Set<Vehicule> getVehicule() {
		return vehicule;
	}

	public void setVehicule(Set<Vehicule> vehicule) {
		this.vehicule = vehicule;
	}

	public String getEmailAddress() {
		return emailAddress.toString();
	}

	/**
	 * Sets the {@link Customer}'s {@link EmailAddress}.
	 * 
	 * @param emailAddress must not be {@literal null}.
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = new EmailAddress(emailAddress);
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNum=" + phoneNum
				+ ", emailAddress=" + emailAddress + "]";
	}

	
	
}