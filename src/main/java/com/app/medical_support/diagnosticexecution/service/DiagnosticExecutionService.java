package com.app.medical_support.diagnosticexecution.service;


import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;

import java.util.List;

public interface DiagnosticExecutionService {

    List<SpecimenDTO> searchSpecimen(String searchType, String searchValue);
    List<SpecimenDTO> findSpecimenList();
    SpecimenDTO findSpecimenDetail(String id);
    SpecimenDTO registerSpecimen(SpecimenDTO specimenDTO);
    SpecimenDTO modifySpecimen(String id, SpecimenDTO specimenDTO);
    void deleteSpecimen(String id);

    List<TestExecutionDTO> findTestExecutionList();
    TestExecutionDTO findTestExecutionDetail(Long id);

}


