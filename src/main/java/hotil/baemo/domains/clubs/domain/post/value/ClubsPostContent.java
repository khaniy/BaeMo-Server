package hotil.baemo.domains.clubs.domain.post.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClubsPostContent(
        @NotBlank
        @Size(min = 1, max = 30_000)
        String content
) {

    public ClubsPostContent(String content) {
        this.content = content;
        BaemoValueObjectValidator.valid(this);
    }
}