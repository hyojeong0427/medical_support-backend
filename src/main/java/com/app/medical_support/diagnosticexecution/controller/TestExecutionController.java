package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.TestExecutionDTO;
import com.app.medical_support.diagnosticexecution.service.DiagnosticExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/testExecution")
@RequiredArgsConstructor
@Tag(name = "TestExecution", description = "검사 수행 API")
public class TestExecutionController {

    private final DiagnosticExecutionService testExecutionService;

    @Operation(summary = "검사 수행 목록 조회", description = "모든 검사 수행 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TestExecutionDTO>>> findList() {
        List<TestExecutionDTO> testExecutionList = testExecutionService.findTestExecutionList();
        ApiResponse<List<TestExecutionDTO>> response =
                new ApiResponse<>(true, "검사 수행 목록 조회 성공", testExecutionList);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "검사 수행 단건 조회", description = "검사 수행 한 건을 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TestExecutionDTO>> findTestExecutionDetail(
            @Parameter(description = "검사 수행 ID")
            @PathVariable String id
    ) {
        TestExecutionDTO result = testExecutionService.findTestExecutionDetail(id);
        ApiResponse<TestExecutionDTO> response =
                new ApiResponse<>(true, "검사 수행 단건 조회 성공", result);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "검사 수행 등록", description = "검사 수행을 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<TestExecutionDTO>> registerTestExecution(
            @Parameter(description = "검사 수행 등록 요청 데이터")
            @RequestBody TestExecutionDTO testExecutionDTO
    ) {
        TestExecutionDTO result = testExecutionService.registerTestExecution(testExecutionDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "검사 수행 등록 성공", result));
    }
}
