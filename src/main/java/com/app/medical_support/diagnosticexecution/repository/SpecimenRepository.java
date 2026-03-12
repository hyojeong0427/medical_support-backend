package com.app.medical_support.diagnosticexecution.repository;

import com.app.medical_support.diagnosticexecution.entity.SpecimenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecimenRepository extends JpaRepository<SpecimenEntity, String> {

}
