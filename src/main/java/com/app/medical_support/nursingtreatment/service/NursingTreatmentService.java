package com.app.medical_support.nursingtreatment.service;

import com.app.medical_support.nursingtreatment.dto.RecordDTO;

import java.util.List;

public interface NursingTreatmentService {
    List<RecordDTO> search(String searchType, String searchValue );
    List<RecordDTO> findRecordList();
    RecordDTO findRecordDetail(String nursingId);
    RecordDTO registerRecord(RecordDTO recordDTO);
    RecordDTO modifyRecord(String nursingId, RecordDTO recordDTO);
    RecordDTO updateRecordStatus(String nursingId, String recordDTO);
}
