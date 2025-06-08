package com.mosque.service;

import com.mosque.dto.RecipientDTO;
import com.mosque.mapper.RecipientDTOMapper;
import com.mosque.model.RecipientEntity;
import com.mosque.repositories.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("recipientService")
public class RecipientServiceImpl implements RecipientService {

    @Autowired
    RecipientRepository recipientRepository;

    @Override
    public Page<RecipientDTO> findAllWithFilter(Specification<RecipientEntity> spec, Pageable pageable) {

        Page<RecipientEntity> page = recipientRepository
                .findAll(spec, pageable);

        return page.map(RecipientDTOMapper::toDto);
    }

    @Override
    public List<RecipientDTO> findAllRecipients() {
        return RecipientDTOMapper.toDto(recipientRepository.findAll());
    }

    @Override
    public Optional<RecipientDTO> findByPhoneNumber(String phoneNumber) {
        return this
                .recipientRepository.findByPhoneNumber(phoneNumber)
                .map(RecipientDTOMapper::toDto);
    }

    @Override
    public RecipientDTO getRecipientById(Long id) {

        RecipientEntity recipient = recipientRepository.findById(id).
                orElseThrow(() -> new RuntimeException(String.format("Recipient with Id: %d is not found", id)));

        return RecipientDTOMapper.toDto(recipient);
    }

    @Override
    @Transactional
    public RecipientDTO addRecipient(RecipientDTO recipient) {

        RecipientEntity entity = RecipientDTOMapper.toEntity(recipient);

        RecipientEntity savedEntity = recipientRepository.save(entity);

        return RecipientDTOMapper.toDto(savedEntity);
    }

    @Override
    public RecipientDTO updateRecipient(RecipientDTO recipientDTO) {

        if (recipientRepository.findById(recipientDTO.id()).isEmpty()) {
            throw (new RuntimeException(String.format("Recipient with Id: %d is not found", recipientDTO.id())));
        }

        RecipientEntity entity = recipientRepository
                .save(RecipientDTOMapper.toEntity(recipientDTO));

        return RecipientDTOMapper.toDto(entity);
    }

    @Override
    public void deleteRecipient(Long id) {
        recipientRepository.deleteById(id);
    }

}