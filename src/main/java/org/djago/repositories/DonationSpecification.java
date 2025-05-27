package org.djago.repositories;

import org.djago.model.Donation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class DonationSpecification {

    public static Specification<Donation> getFilterSpecification(String filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter == null || filter.trim().isEmpty()) {
                return criteriaBuilder.conjunction(); // no filter
            }

            String[] criteria = filter.split(",");
            List<Predicate> predicates = new ArrayList<>();

            for (String criterion : criteria) {
                try {
                    if (criterion.contains(":")) {
                        String[] parts = criterion.split(":", 2); // limit to 2
                        if (parts.length == 2 && !parts[0].isBlank() && !parts[1].isBlank()) {
                            predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get(parts[0].trim())),
                                    "%" + parts[1].trim().toLowerCase() + "%"
                            ));
                        }
                    } else if (criterion.contains(">")) {
                        String[] parts = criterion.split(">", 2);
                        if (parts.length == 2) {
                            predicates.add(criteriaBuilder.greaterThan(
                                    root.get(parts[0].trim()), parts[1].trim()
                            ));
                        }
                    } else if (criterion.contains("<")) {
                        String[] parts = criterion.split("<", 2);
                        if (parts.length == 2) {
                            predicates.add(criteriaBuilder.lessThan(
                                    root.get(parts[0].trim()), parts[1].trim()
                            ));
                        }
                    } else {
                        // Unknown format
                        System.out.println("Invalid filter format: " + criterion);
                    }
                } catch (IllegalArgumentException e) {
                    // This can happen if field name doesn't exist in root.get()
                    System.out.println("Invalid field in filter: " + criterion);
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}