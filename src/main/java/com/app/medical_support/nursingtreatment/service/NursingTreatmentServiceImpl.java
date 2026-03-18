package com.app.medical_support.nursingtreatment.service;

import com.app.medical_support.nursingtreatment.dto.RecordDTO;
import com.app.medical_support.nursingtreatment.dto.RecordRequestDTO;
import com.app.medical_support.nursingtreatment.dto.RecordResponseDTO;
import com.app.medical_support.nursingtreatment.entity.RecordEntity;
import com.app.medical_support.nursingtreatment.exception.RecordNotFoundException;
import com.app.medical_support.nursingtreatment.mapper.RecordMapper;
import com.app.medical_support.nursingtreatment.mapstruct.RecordReqMapStruct;
import com.app.medical_support.nursingtreatment.mapstruct.RecordResMapStruct;

import com.app.medical_support.nursingtreatment.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NursingTreatmentServiceImpl implements NursingTreatmentService {
        private final RecordRepository recordRepository;
        private final RecordReqMapStruct recordReqMapStruct;
        private final RecordResMapStruct recordResMapStruct;
        private final RecordMapper recordMapper;

    @Override
    public List<RecordResponseDTO> search(String searchType, String searchValue, String startDate, String endDate) {

          return recordMapper.search( searchType,  searchValue,  startDate,  endDate);


    }

    @Override
    public List<RecordResponseDTO> findRecordList() {
        return recordMapper.findRecordList();
    }


    @Override
    public RecordResponseDTO findRecordDetail(String id) {
        log.info("Record detail nursingId={} 로 간호 기록 단건 조회 메서드가 실행됩니다.", id);

        RecordResponseDTO recordResponseDTO = recordMapper.findRecordDetail(id);
        if (recordResponseDTO == null) {
            throw new RecordNotFoundException(id);
        }

        return recordResponseDTO;
    }

    @Override
    @Transactional
    public RecordDTO registerRecord(RecordRequestDTO recordRequestDTO) {
        log.info("간호 기록 신규 생성 메서드가 실행됩니다");

        RecordEntity entity = recordReqMapStruct.toEntity(recordRequestDTO);

        log.info("create dto={}", entity);
        log.info("recordedAt={}", entity.getRecordedAt());
        log.info("visitId={}", entity.getVisitId());
        log.info("nursingId={}", entity.getNursingId());


        if (!hasText(entity.getRecordId())) {
            entity.setRecordId(createRecordId());
        }

        if (entity.getNursingId() == null || entity.getNursingId().trim().isEmpty()) {
            entity.setNursingId("NUR_" + System.currentTimeMillis());

        }
        entity.setStatus("ACTIVE");
        RecordEntity newRecord = recordRepository.save(entity);
        return recordResMapStruct.toDTO(newRecord);
    }


    @Override
    @Transactional
    public RecordDTO modifyRecord(String id, RecordDTO recordDTO) {
        log.info("Modify record id={} 간호 기록 수정 메서드가 실행됩니다", id);

        RecordEntity saved = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        saved.setSystolicBp(recordDTO.getSystolicBp());
        saved.setDiastolicBp(recordDTO.getDiastolicBp());
        saved.setPulse(recordDTO.getPulse());
        saved.setRespiration(recordDTO.getRespiration());
        saved.setTemperature(recordDTO.getTemperature());
        saved.setSpo2(recordDTO.getSpo2());
        saved.setObservation(recordDTO.getObservation());
        saved.setPainScore(recordDTO.getPainScore());
        saved.setConsciousnessLevel(recordDTO.getConsciousnessLevel());
        saved.setInitialAssessment(recordDTO.getInitialAssessment());
        saved.setStatus(recordDTO.getStatus());
        saved.setVisitId(recordDTO.getVisitId());
        saved.setNursingId(recordDTO.getNursingId());
        saved.setUpdatedAt(recordDTO.getUpdatedAt());

        RecordEntity updated = recordRepository.save(saved);
        return recordResMapStruct.toDTO(updated);
    }

//    @Override
//    @Transactional
//    public void deactiveRecord(String id) {
//        log.info("deactive record id={} 기록 비활성화 메서드가 실행됩니다", id);
//
//        RecordEntity entity = recordRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException(id));
//
//        entity.setStatus("INACTIVE");
//
//        recordRepository.save(entity);
//
//    }

//    @Override
//    @Transactional
//    public void activeRecord(String id) {
//        log.info("active record id={} 기록 활성화 메서드가 실행됩니다", id);
//
//        RecordEntity entity = recordRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException(id));
//
//        entity.setStatus("ACTIVE");
//
//        recordRepository.save(entity);
//
//    }


    @Override
    @Transactional
    public RecordDTO updateRecordStatus(String id, String status) {
        RecordEntity entity = recordRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        entity.setStatus(status);
        recordRepository.save(entity);

        return recordResMapStruct.toDTO(entity);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }


    private String createRecordId() {
        return "REC_" + System.currentTimeMillis();
    }


}
