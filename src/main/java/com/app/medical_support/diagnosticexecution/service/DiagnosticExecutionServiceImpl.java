package com.app.medical_support.diagnosticexecution.service;

import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.entity.SpecimenEntity;
import com.app.medical_support.diagnosticexecution.exception.SpecimenNotFoundException;
import com.app.medical_support.diagnosticexecution.mapper.SpecimenMapper;
import com.app.medical_support.diagnosticexecution.mapstruct.SpecimenReqMapStruct;
import com.app.medical_support.diagnosticexecution.mapstruct.SpecimenResMapStruct;
import com.app.medical_support.diagnosticexecution.repository.SpecimenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagnosticExecutionServiceImpl implements DiagnosticExecutionService {

    private final SpecimenRepository specimenRepository;
    private final SpecimenReqMapStruct specimenReqMapStruct;
    private final SpecimenResMapStruct specimenResMapStruct;
    private final SpecimenMapper specimenMapper;



    @Override
    public List<SpecimenDTO> searchSpecimen (String searchType, String searchValue){
        log.info("검색 service 호출 searchType={}, searchValue={}", searchType, searchValue );
        if (searchType == null || searchType.isEmpty()){
            throw new SpecimenNotFoundException("검색 타입이 필요합니다");
        }
        List<SpecimenEntity> entities =
                specimenMapper.searchSpecimen(searchType, searchValue);
        return specimenResMapStruct.toDTOList(entities);
    }


    @Override
    public List<SpecimenDTO> findSpecimenList() {
        log.info("검체 전체 조회");
        List<SpecimenEntity> entities = specimenRepository.findAll();
        return specimenResMapStruct.toDTOList(entities);
    }

    @Override
    public SpecimenDTO findSpecimenDetail(String id) {
        log.info("Specimen detail id={} 로 검체 단건 조회 메서드가 실행됩니다.", id);

        SpecimenEntity entity = specimenRepository.findById(id).
                orElseThrow(()-> new IllegalArgumentException("해당 검체가 존재하지 않습니다"));

        return specimenResMapStruct.toDTO(entity);
    }

    @Override
    @Transactional
    public SpecimenDTO registerSpecimen(SpecimenDTO specimenDTO) {
        log.info("검체 신규 생성 메서드가 실행됩니다");
        SpecimenEntity entity = specimenReqMapStruct.toEntity(specimenDTO);

        if (entity.getSpecimenId() == null || entity.getSpecimenId().trim().isEmpty()) {
            entity.setSpecimenId("SP_" + System.currentTimeMillis() );

        }
        SpecimenEntity newSpecimen = specimenRepository.save(entity);
        newSpecimen.setStatus("ACTIVE");
        return specimenResMapStruct.toDTO(newSpecimen);
    }


    @Override
    @Transactional
    public SpecimenDTO modifySpecimen(String id, SpecimenDTO specimenDTO) {
        log.info("Modify specimen id={} 검체 수정 메서드가 실행됩니다", id);

        SpecimenEntity saved = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("수정할 검체가 존재하지 않습니다"));

        saved.setTestExecutionId(specimenDTO.getTestExecutionId());
        saved.setSpecimenStatus(specimenDTO.getSpecimenStatus());
        saved.setSpecimenType(specimenDTO.getSpecimenType());
        saved.setCollectedById(specimenDTO.getCollectedById());
        saved.setStatus(specimenDTO.getStatus());

        SpecimenEntity updated = specimenRepository.save(saved);
        return specimenResMapStruct.toDTO(updated);
    }

    @Override
    @Transactional
    public void deleteSpecimen(String id) {
        log.info("Delete specimen id={} 검체 삭제 메서드가 실행됩니다", id);

        SpecimenEntity entity = specimenRepository.findById(id)
                .orElseThrow(() -> new SpecimenNotFoundException("비활성화 할 검체가 존재하지 않습니다"));

        entity.setStatus("INACTIVE");

        specimenRepository.save(entity);

    }
    }




