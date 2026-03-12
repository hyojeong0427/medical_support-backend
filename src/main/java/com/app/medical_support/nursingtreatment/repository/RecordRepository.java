package com.app.medical_support.nursingtreatment.repository;

import com.app.medical_support.nursingtreatment.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<RecordEntity, String> {
//재정의, 프로시저를 생성해서 여기다가 쓸 수 있다. 간호사 아이디는 NUR_ 로 저장할 수 있도록
}
