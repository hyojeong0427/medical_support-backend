package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;
import com.app.medical_support.diagnosticexecution.service.DiagnosticExecutionService;
import com.app.medical_support.nursingtreatment.dto.RecordResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/testExecution")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "TestExecution", description = "Test execution API")
public class TestExecutionController {

    private final DiagnosticExecutionService testExecutionService;

    @Operation(summary = "TestExecution list", description = "검사 수행 목록 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestExecutionDTO>>> findList() {
        List<TestExecutionDTO> testExecutionList = testExecutionService.findTestExecutionList();
        ApiResponse<List<TestExecutionDTO>> response =
                new ApiResponse<>(true, "Test execution list loaded.", testExecutionList);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "TestExecution detail", description = "검사 수행 단건 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TestExecutionDTO>> findTestExecutionDetail(
            @Parameter(description = "검사 수행 아이디")
            @PathVariable Long id) {

        TestExecutionDTO result = testExecutionService.findTestExecutionDetail(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "검사 수행 단건 조회 성공", result));
    }


}
