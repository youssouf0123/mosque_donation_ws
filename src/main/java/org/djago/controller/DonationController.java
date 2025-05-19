package org.djago.controller;

import org.djago.model.Donation;
import org.djago.service.DonationService;
import org.djago.service.ProductTypeQty;
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

    @GetMapping("/all")
    public ResponseEntity<List<Donation>> getAllDonation() {
        List<Donation> donations = donationService.findAllDonations();
        return new ResponseEntity<>(donations, HttpStatus.OK);
    }

    @GetMapping("/typeQty")
    public ResponseEntity<List<ProductTypeQty>> getDonationQuantityAndType() {

        List<ProductTypeQty> donationTypeQtyList = donationService.getDonationQuantityAndType();

        logger.debug("Youssouf prcdTypeQtyList : " + donationTypeQtyList);

        return new ResponseEntity<>(donationTypeQtyList, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable("id") Long id) {

        Donation donation = donationService.getDonationById(id);

        return new ResponseEntity<>(donation, HttpStatus.OK);
    }

    @PostMapping("/add")
    //@RequestMapping(value = "add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> addDonation(@RequestBody Donation prcd) {

        Set<String> productList = donationService
                .findAllDonations().stream()
                .map(Donation::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(TreeSet::new));

        boolean isProductNameExist = productList.contains(prcd.getName().toLowerCase());

        Donation newPrcd = prcd;

        logger.debug("Youssouf isProductNameExist : " + isProductNameExist);

        if (!isProductNameExist)
            newPrcd = donationService.addDonation(prcd);

        return new ResponseEntity<>(newPrcd, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Donation> updateDonation(@RequestBody Donation prcd) {

        Set<String> productList = donationService.findAllDonations().stream().map(Donation::getName).map(x -> x.toLowerCase()).collect(Collectors.toCollection(TreeSet::new));

        Map<String, Long> mapNameId = donationService
                .findAllDonations()
                .stream()
                .collect(Collectors.toMap(Donation::getName, Donation::getId));

        boolean isProductUpdatable = false;

        logger.debug("Youssouf Product prcd : " + prcd);
        logger.debug("Youssouf Product mapNameId : " + mapNameId);

        //This will prevent to edit a product to an existing product
        for (String str : mapNameId.keySet())
        {
            if (str.equalsIgnoreCase(prcd.getName())) {
                if (mapNameId.get(str).equals(prcd.getId())) {
                    isProductUpdatable = true;
                }
                break;
            }
        }

        //This will allow you to edit the same product
        if (!isProductUpdatable) {
            for (Long id : mapNameId.values()) {
                if (id == prcd.getId()) {
                    if (!productList.contains(prcd.getName().toLowerCase())) {
                        isProductUpdatable = true;
                    }
                    break;
                }
            }
        }

        Donation updatePrcd = prcd;

        logger.debug("Youssouf isProductNameExist : " + isProductUpdatable);

        if (isProductUpdatable) {
            updatePrcd = donationService.updateDonation(prcd);
        }

        return new ResponseEntity<>(updatePrcd, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDonation(@PathVariable("id") Long id) {

        donationService.deleteDonation(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}