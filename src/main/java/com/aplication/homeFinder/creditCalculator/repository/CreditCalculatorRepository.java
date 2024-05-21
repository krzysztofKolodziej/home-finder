package com.aplication.homeFinder.creditCalculator.repository;

import com.aplication.homeFinder.creditCalculator.model.CreditCalculator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCalculatorRepository extends JpaRepository<CreditCalculator, Long> {
}
