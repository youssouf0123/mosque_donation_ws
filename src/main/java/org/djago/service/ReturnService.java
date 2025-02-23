package org.djago.service;

import java.time.LocalDate;
import java.util.List;

import org.djago.model.Return;

public interface ReturnService {

	List<Return> findAll();

	Return findReturnById(Long id);

	List<Return> findReturnsByDate(LocalDate date);

	List<Return> findReturnsByDateRange(LocalDate startDate, LocalDate endDate);

	Return save(Return Return);

	Return updateReturn(Return obj);
}