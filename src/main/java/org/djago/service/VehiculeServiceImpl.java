package org.djago.service;

import java.util.List;

import org.djago.model.Vehicule;
import org.djago.repositories.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("vehiculeService")
@Transactional
public class VehiculeServiceImpl implements VehiculeService {

	@Autowired
	VehiculeRepository vehiculeRepository;
	
	public List<Vehicule> findAllVehicules(){
		return vehiculeRepository.findAll();
	}
	
	public Vehicule getVehiculeById(Long id) {
		return vehiculeRepository.getVehiculeById(id).
				orElseThrow(()->new RuntimeException("id is not found"));
	}
	
	public Vehicule  addVehicule(Vehicule vehicule) {
		return vehiculeRepository.save(vehicule);
	}
	
	public Vehicule  updateVehicule(Vehicule vehicule) {
		if(!vehiculeRepository.getVehiculeById(vehicule.getId()).isPresent()) {
			throw(new RuntimeException("id is not found"));
		}
		return vehiculeRepository.save(vehicule);
	}
	
	public void deleteVehicule(Long id) {
		vehiculeRepository.deleteById(id);
	}
}