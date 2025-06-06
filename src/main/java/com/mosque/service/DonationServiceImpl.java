package com.mosque.service;

import com.mosque.model.Donation;
import com.mosque.repositories.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("donationService")
@Transactional
public class DonationServiceImpl implements DonationService {

    @Autowired
    DonationRepository donationRepository;

    @Override
    public Page<Donation> findAllWithFilter(Specification<Donation> spec, Pageable pageable) {
        return donationRepository.findAll(spec, pageable);
    }

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