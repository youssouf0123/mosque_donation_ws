package com.mosque.repositories;

import com.mosque.service.DonationTypeAndQty;
import com.mosque.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long>, JpaSpecificationExecutor<Donation> {

	Optional<Donation> getDonationById(Long id);

    @Query("SELECT d.donation_type AS donationType, SUM(d.quantity) AS totalQuantity FROM Donation d GROUP BY d.donation_type")
    List<DonationTypeAndQty> getDonationsByTypeAndQuantity();
}