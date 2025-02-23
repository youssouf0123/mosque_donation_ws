package org.djago.controller;

import java.util.ArrayList;
import java.util.List;

import org.djago.model.Vehicule;
import org.djago.service.VehiculeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200", "http://mande-dev.com"}, maxAge = 3600)
@RestController
@RequestMapping("/vehicule")
public class VehiculeController {

	private static final Logger logger = LoggerFactory.getLogger(VehiculeController.class);

	@Autowired
	private VehiculeService vehiculeService;

	@GetMapping("/all")
	public ResponseEntity<List<Vehicule>> getAllVehicule(){
		List<Vehicule> prcdList = vehiculeService.findAllVehicules();
		return new ResponseEntity<>(prcdList, HttpStatus.OK);
	}
	@GetMapping("/find/{id}")
	public ResponseEntity<Vehicule> getVehiculeById(@PathVariable("id") Long id){
		Vehicule prcd = vehiculeService.getVehiculeById(id);
		return new ResponseEntity<Vehicule>(prcd, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	//@RequestMapping(value = "add", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Vehicule> addVehicule(@RequestBody Vehicule prcd){
		Vehicule newPrcd = vehiculeService.addVehicule(prcd);
		return new ResponseEntity<>(newPrcd, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Vehicule> updateVehicule(@RequestBody Vehicule prcd){
			
		Vehicule updatePrcd = vehiculeService.updateVehicule(prcd);
		return new ResponseEntity<>(updatePrcd, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteVehicule(@PathVariable("id") Long id){
		vehiculeService.deleteVehicule(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}