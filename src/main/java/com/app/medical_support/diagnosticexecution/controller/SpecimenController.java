package com.app.medical_support.diagnosticexecution.controller;

import com.app.medical_support.common.ApiResponse;
import com.app.medical_support.diagnosticexecution.dto.SpecimenDTO;
import com.app.medical_support.diagnosticexecution.service.DiagnosticExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/specimen")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Specimen", description = "Specimen API")
public class SpecimenController {

    private final DiagnosticExecutionService specimenService;

    @Operation(summary = "Search specimen", description = "Search by visitId, collectedAt, or specimenType")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<SpecimenDTO>>> searchSpecimens(
            @Parameter(description = "visitId, collectedAt, specimenType")
            @RequestParam("searchType") String searchType,
            @Parameter(description = "Search value")
            @RequestParam("searchValue") String searchValue
    ) {
        List<SpecimenDTO> specimenList = specimenService.searchSpecimen(searchType, searchValue);
        ApiResponse<List<SpecimenDTO>> response =
                new ApiResponse<>(true, "Specimen search completed.", specimenList);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Specimen list", description = "Find all specimens")
    @GetMapping
    public ResponseEntity<ApiResponse<List<SpecimenDTO>>> findList() {
        List<SpecimenDTO> specimenList = specimenService.findSpecimenList();
        ApiResponse<List<SpecimenDTO>> response =
                new ApiResponse<>(true, "Specimen list loaded.", specimenList);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Specimen detail", description = "Find one specimen")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecimenDTO>> findSpecimenDetail(
            @Parameter(description = "Specimen ID")
            @PathVariable String id
    ) {
        SpecimenDTO specimen = specimenService.findSpecimenDetail(id);
        ApiResponse<SpecimenDTO> response =
                new ApiResponse<>(true, "Specimen detail loaded.", specimen);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create specimen", description = "Create a new specimen")
    @PostMapping
    public ResponseEntity<ApiResponse<SpecimenDTO>> registerSpecimen(
            @Parameter(description = "Specimen request body")
            @RequestBody SpecimenDTO specimen
    ) {
        SpecimenDTO savedSpecimen = specimenService.registerSpecimen(specimen);
        ApiResponse<SpecimenDTO> response =
                new ApiResponse<>(true, "Specimen created.", savedSpecimen);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update specimen", description = "Update specimen data")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecimenDTO>> modifySpecimen(
            @Parameter(description = "Specimen ID")
            @PathVariable String id,
            @Parameter(description = "Specimen request body")
            @RequestBody SpecimenDTO specimenDTO
    ) {
        SpecimenDTO updatedSpecimen = specimenService.modifySpecimen(id, specimenDTO);
        ApiResponse<SpecimenDTO> response =
                new ApiResponse<>(true, "Specimen updated.", updatedSpecimen);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete specimen", description = "Change status to N")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeSpecimen(
            @Parameter(description = "Specimen ID")
            @PathVariable String id
    ) {
        specimenService.deleteSpecimen(id);
        ApiResponse<String> response =
                new ApiResponse<>(true, "Specimen deleted.", id);

        return ResponseEntity.ok(response);
    }
}
