package org.djago.service;

import java.time.LocalDate;
import java.util.List;

import org.djago.model.Return;
import org.djago.repositories.ReturnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("returnService")
@Transactional(propagation = Propagation.REQUIRED)
public class ReturnServiceImpl implements ReturnService {

	Logger logger = LoggerFactory.getLogger(ReturnServiceImpl.class);

	@Autowired
	private ReturnRepository returnRepository;

	@Override
	public Return findReturnById(Long id) {
		return returnRepository.findReturnById(id).orElseThrow(() -> new RuntimeException("id is not found"));
	}

	@Override
	public List<Return> findAll() {
		return returnRepository.findAll();
	}

	@Override
	public List<Return> findReturnsByDate(LocalDate date) {
		return returnRepository.findReturnsByDate(date);
	}

	@Override
	public List<Return> findReturnsByDateRange(LocalDate startDate, LocalDate endDate) {
		return returnRepository.findReturnsByDateRange(startDate, endDate);
	}

	@Override
	public Return save(Return Return) {
		return returnRepository.save(Return);
	}

	@Override
	public Return updateReturn(Return obj) {

		if (!returnRepository.findById(obj.getId()).isPresent()) {
			throw (new RuntimeException("id is not found"));
		}
		return returnRepository.save(obj);
	}

}