package hotil.baemo.domains.notice.domain.value.image;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record NoticeImagePath(
    @NotBlank
    String path
) {

    public NoticeImagePath(String path) {
        this.path = path;
        BaemoValueObjectValidator.valid(this);
    }
}
