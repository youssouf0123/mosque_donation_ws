package org.djago.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.djago.model.Order;
import org.djago.model.Return;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {
	
	@Query("SELECT r FROM Return r WHERE r.returnDate = :date")
	List<Return> findReturnsByDate(@Param("date") LocalDate date);
	
	@Query("SELECT r FROM Return r WHERE r.returnDate between :startDate and :endDate")
	List<Return> findReturnsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	@Query("SELECT o FROM Return o WHERE o.id = :id")
	Optional<Return> findReturnById(@Param("id") Long id);
}