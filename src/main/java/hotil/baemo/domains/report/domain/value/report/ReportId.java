package hotil.baemo.domains.report.domain.value.report;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record ReportId(@NotNull Long id) {

    public ReportId(@NotNull Long id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
