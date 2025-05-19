package org.djago.repositories;

import org.djago.model.Donation;
import org.djago.service.ProductTypeQty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

	Optional<Donation> getDonationById(Long id);

    @Query("SELECT d.donation_type AS donationType, SUM(d.quantity) AS totalQuantity FROM Donation d GROUP BY d.donation_type")
    List<ProductTypeQty> getDonationQuantityAndType();
}