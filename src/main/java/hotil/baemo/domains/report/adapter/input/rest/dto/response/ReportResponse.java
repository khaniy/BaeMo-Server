package hotil.baemo.domains.report.adapter.input.rest.dto.response;

import jakarta.validation.constraints.NotNull;

public interface ReportResponse {
    record ReasonDTO(
        @NotNull
        String reason,
        @NotNull
        String description
    ) implements ReportResponse {
    }
}