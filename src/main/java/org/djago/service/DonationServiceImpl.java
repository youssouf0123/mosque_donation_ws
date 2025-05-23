package org.djago.service;

import org.djago.model.Donation;
import org.djago.repositories.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("donationService")
@Transactional
public class DonationServiceImpl implements DonationService {

    @Autowired
    DonationRepository donationRepository;

    @Override
    public List<Donation> findAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public List<DonationTypeAndQty> getDonationsByTypeAndQuantity() {
        return donationRepository.getDonationsByTypeAndQuantity();
    }

    @Override
    public Donation getDonationById(Long id) {
        return donationRepository.getDonationById(id).
                orElseThrow(() -> new RuntimeException("id is not found"));
    }

    @Override
    @Transactional
    public Donation addDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public Donation updateDonation(Donation emp) {
        if (!donationRepository.getDonationById(emp.getId()).isPresent()) {
            throw (new RuntimeException("id is not found"));
        }
        return donationRepository.save(emp);
    }

    @Override
    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }

}