package com.mosque.controller;

import com.mosque.dto.RecipientDTO;
import com.mosque.model.RecipientEntity;
import com.mosque.repositories.RecipientSpecification;
import com.mosque.service.RecipientService;
import io.swagger.v3.oas.annotations.Operation;
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

@CrossOrigin(origins = {"http://localhost:4200", "http://mande-dev.com"}, maxAge = 3600)
@RestController
@RequestMapping("/api/v1/recipient")
public class RecipientController {

    private static final Logger logger = LoggerFactory.getLogger(RecipientController.class);

    @Autowired
    private RecipientService recipientService;

    //    GET /donations?page=0&size=10&filter=donorName:John,amount>100&sort=amount&order=desc
//    Use the @Operation, @Parameter, and @Schema annotations for anything you want to show in Swagger/OpenAPI docs.
    @Operation(summary = "Get all recipient objects", description = "Returns list of recipients") // swagger annotation
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
            Specification<RecipientEntity> spec = RecipientSpecification.getFilterSpecification(filter);
            return recipientService.findAllWithFilter(spec, pageable);
        } else {
            List<RecipientDTO> allRecipients = this.recipientService.findAllRecipients();
            logger.info(allRecipients.toString());
            return allRecipients;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipientDTO> getRecipientById(@PathVariable("id") Long id) {
        RecipientDTO recipient = this.recipientService.getRecipientById(id);
        return new ResponseEntity<>(recipient, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addRecipient(@RequestBody RecipientDTO recipient) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RecipientDTO> updateRecipient(@RequestBody RecipientDTO recipient) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipient(@PathVariable("id") Long id) {
        recipientService.deleteRecipient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}