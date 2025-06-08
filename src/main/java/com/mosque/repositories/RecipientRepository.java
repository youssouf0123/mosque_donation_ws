package com.mosque.repositories;

import com.mosque.model.RecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<RecipientEntity, Long>, JpaSpecificationExecutor<RecipientEntity> {
    Optional<RecipientEntity> findByPhoneNumber(String phoneNumber);
}