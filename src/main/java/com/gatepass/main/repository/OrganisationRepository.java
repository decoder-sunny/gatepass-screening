package com.gatepass.main.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gatepass.main.model.Organisation;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Integer>{




}
