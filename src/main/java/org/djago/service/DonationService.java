package org.djago.service;

import org.djago.model.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface DonationService {

	public Page<Donation> findAllWithFilter(Specification<Donation> spec, Pageable pageable);

	public List<Donation> findAllDonations();
	
	public List<DonationTypeAndQty> getDonationsByTypeAndQuantity();
	
	public Donation getDonationById(Long id);
	
	public Donation  addDonation(Donation emp);
	
	public Donation  updateDonation(Donation emp);
	
	public void deleteDonation(Long id);
}