package org.djago.repositories;

import java.util.Optional;

import org.djago.model.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
	Optional<Vehicule> getVehiculeById(Long id);
}