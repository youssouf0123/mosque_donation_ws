package com.mosque.service;

import com.mosque.dto.RecipientRecord;
import com.mosque.mapper.RecipientRecordMapper;
import com.mosque.model.RecipientEntity;
import com.mosque.repositories.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("recipientService")
@Transactional
public class RecipientServiceImpl implements RecipientService {

    @Autowired
    RecipientRepository recipientRepository;

    @Override
    public Page<RecipientRecord> findAllWithFilter(Specification<RecipientEntity> spec, Pageable pageable) {

        Page<RecipientEntity> page = recipientRepository
                .findAll(spec, pageable);

        return page.map(RecipientRecordMapper::toDto);
    }

    @Override
    public List<RecipientRecord> findAllRecipients() {
        return RecipientRecordMapper.toDto(recipientRepository.findAll());
    }

    @Override
    public RecipientRecord getRecipientById(Long id) {

        RecipientEntity recipient = recipientRepository.findById(id).
                orElseThrow(() -> new RuntimeException("id is not found"));

        return RecipientRecordMapper.toDto(recipient);
    }

    @Override
    @Transactional
    public RecipientRecord addRecipient(RecipientRecord recipient) {

        RecipientEntity entity = recipientRepository
                .save(RecipientRecordMapper.toEntity(recipient));

        return RecipientRecordMapper.toDto(entity);
    }

    @Override
    public RecipientRecord updateRecipient(RecipientRecord recipientDTO) {

        if (!recipientRepository.findById(recipientDTO.id()).isPresent()) {
            throw (new RuntimeException("id is not found"));
        }

        RecipientEntity entity = recipientRepository
                .save(RecipientRecordMapper.toEntity(recipientDTO));

        return RecipientRecordMapper.toDto(entity);
    }

    @Override
    public void deleteRecipient(Long id) {
        recipientRepository.deleteById(id);
    }

}