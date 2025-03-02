package com.aplication.homeFinder.creditcalculator.repository;

import com.aplication.homeFinder.creditcalculator.model.CreditCalculator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCalculatorRepository extends JpaRepository<CreditCalculator, Long> {
}
