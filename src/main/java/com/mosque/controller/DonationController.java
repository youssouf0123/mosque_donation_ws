package com.mosque.controller;

import com.mosque.service.DonationTypeAndQty;
import com.mosque.model.Donation;
import com.mosque.repositories.DonationSpecification;
import com.mosque.service.DonationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
@RequestMapping("/api/v1/donations")
public class DonationController {

    private static final Logger logger = LoggerFactory.getLogger(DonationController.class);

    @Autowired
    private DonationService donationService;

    //    GET /donations?page=0&size=10&filter=donorName:John,amount>100&sort=amount&order=desc
//    Use the @Operation, @Parameter, and @Schema annotations for anything you want to show in Swagger/OpenAPI docs.
    @Operation(summary = "Get all donation objects", description = "Returns list of donations") // swagger annotation
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "Successful operation"),
//            @ApiResponse(responseCode = "404", description = "User not found")
//    })
    @GetMapping
    public Object getAllDonationsWithServerSidePagination(
            @Parameter(description = "page number for pagination", required = true)
            @RequestParam(required = false) Integer page,

            @Parameter(description = "page size for pagination", required = true)
            @RequestParam(required = false) Integer size,

            @Parameter(description = "filter for filtering result", required = true)
            @RequestParam(required = false) String filter,

            @Parameter(description = "sort column to be used", required = true)
            @RequestParam(required = false) String sort,

            @Parameter(description = "order sort order (asc/desc)", required = true)
            @RequestParam(required = false) String order
    ) {
        if (page != null && size != null) {
            Pageable pageable = PageRequest.of(
                    page,
                    size,
                    order != null && sort != null ?
                            Sort.by(Sort.Direction.fromString(order), sort) : Sort.unsorted()
            );
            Specification<Donation> spec = DonationSpecification.getFilterSpecification(filter);
            return donationService.findAllWithFilter(spec, pageable);
        } else {
            return this.donationService.findAllDonations();
        }
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
        for (String str : mapNameId.keySet()) {
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