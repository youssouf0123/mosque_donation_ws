package com.mosque.service;

import com.mosque.dto.RecipientRecord;
import com.mosque.model.RecipientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RecipientService {

    public Page<RecipientRecord> findAllWithFilter(Specification<RecipientEntity> spec, Pageable pageable);

    public List<RecipientRecord> findAllRecipients();

    public RecipientRecord getRecipientById(Long id);

    public RecipientRecord addRecipient(RecipientRecord recipientDTO);

    public RecipientRecord updateRecipient(RecipientRecord recipientDTO);

    public void deleteRecipient(Long id);
}