package com.aplication.homeFinder.agent.respository;

import com.aplication.homeFinder.agent.model.EstateAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstateAgentRepository extends JpaRepository<EstateAgent,Long> {
}
