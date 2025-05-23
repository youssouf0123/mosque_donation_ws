package org.djago.service;

import org.djago.model.Donation;

import java.util.List;


public interface DonationService {

	public List<Donation> findAllDonations();
	
	public List<DonationTypeAndQty> getDonationsByTypeAndQuantity();
	
	public Donation getDonationById(Long id);
	
	public Donation  addDonation(Donation emp);
	
	public Donation  updateDonation(Donation emp);
	
	public void deleteDonation(Long id);
}