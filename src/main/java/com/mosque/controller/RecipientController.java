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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200", "http://mande-dev.com"}, maxAge = 3600)
@RestController
@RequestMapping("/api/v1/recipients")
public class RecipientController {

    /**
     * 5️⃣ Optional: Use an Idempotency-Key
     * In more advanced APIs, clients send a header:
     * <p>
     * Idempotency-Key: 123e4567-e89b-12d3-a456-426614174000
     * Your backend stores this key and result → if same key is sent again, it returns the same response without re-processing.
     * This is how Stripe API works for example → allows safe retries on POST.
     * If you want, I can show you how to add Idempotency-Key support to your POST method → it's a cool pattern.
     */
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
    public Object getAllRecipients(
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
                    (order != null && sort != null)
                            ? Sort.by(Sort.Direction.fromString(order), sort)
                            : Sort.unsorted()
            );

            Specification<RecipientEntity> spec = RecipientSpecification.getFilterSpecification(filter);
            Page<RecipientDTO> resultPage = recipientService.findAllWithFilter(spec, pageable);

            return ResponseEntity.ok(resultPage);
        } else {
            List<RecipientDTO> recipients = recipientService.findAllRecipients();
            return ResponseEntity.ok(recipients);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipientDTO> getRecipientById(@PathVariable("id") Long id) {
        RecipientDTO recipient = this.recipientService.getRecipientById(id);
        return ResponseEntity.ok(recipient);
    }

    @PostMapping
    public ResponseEntity<RecipientDTO> addRecipient(@RequestBody RecipientDTO recipientDto) {
        // todo: 400 Bad Request (when validation errors, etc)
        Optional<RecipientDTO> existing = recipientService.findByPhoneNumber(recipientDto.phoneNumber());

        if (existing.isPresent()) {
            // Return 200 OK with existing recipient → makes this POST idempotent-like
            return ResponseEntity.ok(existing.get());
        }

        RecipientDTO savedRecipient = recipientService.addRecipient(recipientDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedRecipient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipientDTO> updateRecipient(@RequestBody RecipientDTO recipientDto) {
//    public ResponseEntity<RecipientDTO> updateRecipient(@PathVariable("id") Long id, @RequestBody RecipientDTO recipientDto) { // todo: preferred method to pass ID in URL!
        // todo: Extra tip: you can validate that the resource exists first, to avoid creating accidentally.
        RecipientDTO updatedRecipient = recipientService.updateRecipient(recipientDto);
        return ResponseEntity.ok(updatedRecipient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable("id") Long id) {
        recipientService.deleteRecipient(id);
        return ResponseEntity.noContent().build(); // 204 No Content is preferred for delete
    }

}