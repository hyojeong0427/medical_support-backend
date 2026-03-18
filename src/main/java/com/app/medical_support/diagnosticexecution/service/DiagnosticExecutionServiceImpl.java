package com.app.medical_support.diagnosticexecution.service;

import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionReqDTO;
import com.app.medical_support.diagnosticexecution.entity.SpecimenEntity;
import com.app.medical_support.diagnosticexecution.entity.TestExecutionEntity;
import com.app.medical_support.diagnosticexecution.exception.SpecimenNotFoundException;
import com.app.medical_support.diagnosticexecution.exception.TestExecutionNotFoundExecution;
import com.app.medical_support.diagnosticexecution.mapper.SpecimenMapper;
import com.app.medical_support.diagnosticexecution.mapstruct.SpecimenReqMapStruct;
import com.app.medical_support.diagnosticexecution.mapstruct.SpecimenResMapStruct;
import com.app.medical_support.diagnosticexecution.mapstruct.TestExecutionReqMapStruct;
import com.app.medical_support.diagnosticexecution.mapstruct.TestExecutionResMapStruct;
import com.app.medical_support.diagnosticexecution.repository.SpecimenRepository;
import com.app.medical_support.diagnosticexecution.repository.TestExecutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosticExecutionServiceImpl implements DiagnosticExecutionService {

    private final SpecimenRepository specimenRepository;
    private final SpecimenReqMapStruct specimenReqMapStruct;
    private final SpecimenResMapStruct specimenResMapStruct;
    private final SpecimenMapper specimenMapper;
    private final TestExecutionRepository testExecutionRepository;
    private final TestExecutionReqMapStruct testExecutionReqMapStruct;
    private final TestExecutionResMapStruct testExecutionResMapStruct;

    @Override
    public List<SpecimenDTO> searchSpecimen(String searchType, String searchValue) {
        log.info("Search specimen. searchType={}, searchValue={}", searchType, searchValue);

        if (!hasText(searchType) || !hasText(searchValue)) {
            throw new SpecimenNotFoundException("Search type and search value are required.");
        }

        List<SpecimenEntity> specimenEntityList = specimenMapper.searchSpecimen(searchType, searchValue);
        return specimenResMapStruct.toDTOList(specimenEntityList);
    }

    @Override
    public List<SpecimenDTO> findSpecimenList() {
        log.info("Find specimen list");

        List<SpecimenEntity> specimenEntityList = specimenRepository.findAll();
        return specimenResMapStruct.toDTOList(specimenEntityList);
    }

    @Override
    public SpecimenDTO findSpecimenDetail(String id) {
        log.info("Find specimen detail. id={}", id);

        SpecimenEntity specimenEntity = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("Specimen not found. id=" + id));

        return specimenResMapStruct.toDTO(specimenEntity);
    }

    @Override
    @Transactional
    public SpecimenDTO registerSpecimen(SpecimenDTO specimenDTO) {
        log.info("Register specimen");

        SpecimenEntity specimenEntity = specimenReqMapStruct.toEntity(specimenDTO);

        if (!hasText(specimenEntity.getSpecimenId())) {
            specimenEntity.setSpecimenId(createSpecimenId());
        }

        if (!hasText(specimenEntity.getCreatedBy())) {
            specimenEntity.setCreatedBy("SYSTEM");
        }

        specimenEntity.setStatus(normalizeStatus(specimenEntity.getStatus()));

        SpecimenEntity savedEntity = specimenRepository.save(specimenEntity);
        return specimenResMapStruct.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public SpecimenDTO modifySpecimen(String id, SpecimenDTO specimenDTO) {
        log.info("Modify specimen. id={}", id);

        SpecimenEntity savedEntity = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("Specimen not found. id=" + id));

        savedEntity.setVisitId(specimenDTO.getVisitId());
        savedEntity.setSpecimenType(specimenDTO.getSpecimenType());
        savedEntity.setCollectedAt(specimenDTO.getCollectedAt());

        if (hasText(specimenDTO.getCreatedBy())) {
            savedEntity.setCreatedBy(specimenDTO.getCreatedBy().trim());
        }

        if (hasText(specimenDTO.getStatus())) {
            savedEntity.setStatus(normalizeStatus(specimenDTO.getStatus()));
        }

        SpecimenEntity updatedEntity = specimenRepository.save(savedEntity);
        return specimenResMapStruct.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteSpecimen(String id) {
        log.info("Delete specimen. id={}", id);

        SpecimenEntity specimenEntity = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("Specimen not found. id=" + id));

        specimenEntity.setStatus("N");
        specimenRepository.save(specimenEntity);
    }

    @Override
    public List<TestExecutionDTO> findTestExecutionList() {
        log.info("Find test execution list");

        List<TestExecutionEntity> testExecutionEntityList = testExecutionRepository.findAll();
        return testExecutionResMapStruct.toDTOList(testExecutionEntityList);
    }

    @Override
    public TestExecutionDTO findTestExecutionDetail(String id) {
        log.info("Find test execution detail. id={}", id);

        TestExecutionEntity testExecutionEntity = testExecutionRepository.findById(id)
                .orElseThrow(() -> new TestExecutionNotFoundExecution("Test execution not found. id=" + id));

        return testExecutionResMapStruct.toDTO(testExecutionEntity);
    }

    @Override
    @Transactional
    public TestExecutionDTO registerTestExecution(TestExecutionDTO testExecutionDTO) {
        log.info("Register test execution");

        TestExecutionEntity testExecutionEntity = testExecutionReqMapStruct.toEntity(testExecutionDTO);

        if (!hasText(testExecutionEntity.getTestExecutionId())) {
            testExecutionEntity.setTestExecutionId(createTestExecutionId());
        }

        testExecutionEntity.setCreatedAt(LocalDateTime.now());

        if (!hasText(testExecutionEntity.getProgressStatus())) {
            testExecutionEntity.setProgressStatus("WAITING");
        }

        if (testExecutionEntity.getRetryNo() == null) {
            testExecutionEntity.setRetryNo(0);
        }

        TestExecutionEntity savedEntity = testExecutionRepository.save(testExecutionEntity);
        return testExecutionResMapStruct.toDTO(savedEntity);
    }

    @Override
    @Transactional
    public TestExecutionDTO modifyTestExecution(String id, TestExecutionReqDTO testExecutionReqDTO) {
        log.info("Modify test execution. id={}", id);

        TestExecutionEntity savedEntity = testExecutionRepository.findById(id)
                .orElseThrow(() -> new TestExecutionNotFoundExecution("Test execution not found. id=" + id));

        savedEntity.setProgressStatus(testExecutionReqDTO.getProgressStatus());
        savedEntity.setRetryNo(testExecutionReqDTO.getRetryNo());
        savedEntity.setStartedAt(testExecutionReqDTO.getStartedAt());
        savedEntity.setCompletedAt(testExecutionReqDTO.getCompletedAt());
        savedEntity.setPerformerId(testExecutionReqDTO.getPerformerId());
        savedEntity.setUpdatedAt(LocalDateTime.now());

        TestExecutionEntity updatedEntity = testExecutionRepository.save(savedEntity);
        return testExecutionResMapStruct.toDTO(updatedEntity);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String createSpecimenId() {
        return "SPECIMEN_" + System.currentTimeMillis();
    }

    private String createTestExecutionId() {
        return "TEST_EXECUTION_" + System.currentTimeMillis();
    }

    private String normalizeStatus(String status) {
        if (!hasText(status)) {
            return "Y";
        }

        String trimmedStatus = status.trim().toUpperCase();

        if ("ACTIVE".equals(trimmedStatus) || "Y".equals(trimmedStatus)) {
            return "Y";
        }

        if ("INACTIVE".equals(trimmedStatus) || "N".equals(trimmedStatus)) {
            return "N";
        }

        return trimmedStatus;
    }
}
