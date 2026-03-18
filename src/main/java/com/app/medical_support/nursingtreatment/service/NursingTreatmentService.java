package com.app.medical_support.nursingtreatment.service;

import com.app.medical_support.nursingtreatment.dto.RecordDTO;
import com.app.medical_support.nursingtreatment.dto.RecordRequestDTO;
import com.app.medical_support.nursingtreatment.dto.RecordResponseDTO;

import java.util.List;

public interface NursingTreatmentService {
    List<RecordResponseDTO> search(String searchType, String searchValue, String startDate, String endDate);
    List<RecordResponseDTO> findRecordList();
    RecordResponseDTO findRecordDetail(String nursingId);
    RecordDTO registerRecord(RecordRequestDTO recordRequestDTO);
    RecordDTO modifyRecord(String nursingId, RecordDTO recordDTO);
    RecordDTO updateRecordStatus(String nursingId, String recordDTO);
}
