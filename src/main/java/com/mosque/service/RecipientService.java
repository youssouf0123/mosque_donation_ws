package com.mosque.service;

import com.mosque.dto.RecipientDTO;
import com.mosque.model.RecipientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface RecipientService {

    public Page<RecipientDTO> findAllWithFilter(Specification<RecipientEntity> spec, Pageable pageable);

    public List<RecipientDTO> findAllRecipients();

    public Optional<RecipientDTO> findByPhoneNumber(String phoneNumber);

    public RecipientDTO getRecipientById(Long id);

    public RecipientDTO addRecipient(RecipientDTO recipientDTO);

    public RecipientDTO updateRecipient(RecipientDTO recipientDTO);

    public void deleteRecipient(Long id);
}