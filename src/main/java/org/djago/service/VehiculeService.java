package org.djago.service;

import java.util.List;

import org.djago.model.Vehicule;

public interface VehiculeService {

	public List<Vehicule> findAllVehicules();
	
	public Vehicule getVehiculeById(Long id);
	
	public Vehicule  addVehicule(Vehicule vehicule);
	
	public Vehicule  updateVehicule(Vehicule vehicule);
	
	public void deleteVehicule(Long id);
}