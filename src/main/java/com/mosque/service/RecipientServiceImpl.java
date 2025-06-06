package com.mosque.service;

import com.mosque.dto.RecipientDTO;
import com.mosque.mapper.RecipientMapper;
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

    @Autowired
    private final RecipientMapper recipientMapper;

    public RecipientServiceImpl(RecipientMapper recipientMapper) {
        this.recipientMapper = recipientMapper;
    }

    @Override
    public Page<RecipientDTO> findAllWithFilter(Specification<RecipientEntity> spec, Pageable pageable) {

        Page<RecipientEntity> page = recipientRepository
                .findAll(spec, pageable);

        return page.map(recipientMapper::toDto);
    }

    @Override
    public List<RecipientDTO> findAllRecipients() {
        return RecipientMapper.INSTANCE.toDto(recipientRepository.findAll());
    }

    @Override
    public RecipientDTO getRecipientById(Long id) {

        RecipientEntity recipient = recipientRepository.findById(id).
                orElseThrow(() -> new RuntimeException("id is not found"));

        return RecipientMapper.INSTANCE.toDto(recipient);
    }

    @Override
    @Transactional
    public RecipientDTO addRecipient(RecipientDTO recipient) {

        RecipientEntity entity = recipientRepository
                .save(RecipientMapper.INSTANCE.toEntity(recipient));

        return RecipientMapper.INSTANCE.toDto(entity);
    }

    @Override
    public RecipientDTO updateRecipient(RecipientDTO recipientDTO) {

        if (!recipientRepository.findById(recipientDTO.getId()).isPresent()) {
            throw (new RuntimeException("id is not found"));
        }

        RecipientEntity entity = recipientRepository
                .save(RecipientMapper.INSTANCE.toEntity(recipientDTO));

        return RecipientMapper.INSTANCE.toDto(entity);
    }

    @Override
    public void deleteRecipient(Long id) {
        recipientRepository.deleteById(id);
    }

}