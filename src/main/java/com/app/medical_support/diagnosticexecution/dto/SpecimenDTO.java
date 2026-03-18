package com.app.medical_support.diagnosticexecution.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "Specimen data")
@Getter
@Setter
@NoArgsConstructor
public class SpecimenDTO {

    @Schema(description = "Specimen ID")
    private String specimenId;

    @Schema(description = "Visit ID")
    private String visitId;

    @Schema(description = "Specimen type")
    private String specimenType;

    @Schema(description = "Collected date time")
    private LocalDateTime collectedAt;

    @Schema(description = "Created by")
    private String createdBy;

    @Schema(description = "Status")
    private String status;
}
