package org.djago.controller;

import org.djago.model.Donation;
import org.djago.service.DonationService;
import org.djago.service.DonationTypeAndQty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "http://mande-dev.com"}, maxAge = 3600)
@RestController
@RequestMapping("/donation")
public class DonationController {

    private static final Logger logger = LoggerFactory.getLogger(DonationController.class);

    @Autowired
    private DonationService donationService;

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonation() {
        List<Donation> donations = this.donationService.findAllDonations();
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    @GetMapping("/ByTypeAndQty")
    public ResponseEntity<List<DonationTypeAndQty>> getDonationsByTypeAndQuantity() {

        List<DonationTypeAndQty> donationTypeQtyList = this.donationService
                .getDonationsByTypeAndQuantity();

        logger.debug("Youssouf donationTypeQtyList : " + donationTypeQtyList);

        return new ResponseEntity<>(donationTypeQtyList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") Long id) {
        Donation donation = this.donationService.getDonationById(id);
        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addDonation(@RequestBody Donation donation) {

        Set<String> donationsList = this.donationService
                .findAllDonations().stream()
                .map(Donation::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(TreeSet::new));

        boolean isExistingDonation = donationsList.contains(donation.getName().toLowerCase());

        Donation newDonation = donation;

        logger.debug("Youssouf - isExistingDonation : " + isExistingDonation);

        if (!isExistingDonation)
            newDonation = this.donationService.addDonation(donation);

        return new ResponseEntity<>(newDonation, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Donation> updateDonation(@RequestBody Donation donation) {

        Set<String> donationList = this.donationService
                .findAllDonations()
                .stream()
                .map(Donation::getName)
                .map(x -> x.toLowerCase())
                .collect(Collectors.toCollection(TreeSet::new));

        Map<String, Long> mapNameId = this.donationService
                .findAllDonations()
                .stream()
                .collect(Collectors.toMap(Donation::getName, Donation::getId));

        boolean isDonationUpdatable = false;

        logger.debug("Youssouf donation : " + donation);
        logger.debug("Youssouf donation mapNameId : " + mapNameId);

        //This will prevent to edit to an existing donation
        for (String str : mapNameId.keySet())
        {
            if (str.equalsIgnoreCase(donation.getName())) {
                if (mapNameId.get(str).equals(donation.getId())) {
                    isDonationUpdatable = true;
                }
                break;
            }
        }

        //This will allow you to edit the same donation
        if (!isDonationUpdatable) {
            for (Long id : mapNameId.values()) {
                if (id.equals(donation.getId())) {
                    if (!donationList.contains(donation.getName().toLowerCase())) {
                        isDonationUpdatable = true;
                    }
                    break;
                }
            }
        }

        Donation updatePrcd = donation;

        logger.debug("Youssouf isDonationUpdatable : " + isDonationUpdatable);

        if (isDonationUpdatable) {
            updatePrcd = donationService.updateDonation(donation);
        }

        return new ResponseEntity<>(updatePrcd, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDonation(@PathVariable("id") Long id) {
        donationService.deleteDonation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}