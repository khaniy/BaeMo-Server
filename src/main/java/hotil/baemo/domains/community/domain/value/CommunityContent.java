package hotil.baemo.domains.community.domain.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CommunityContent(
    @NotBlank
    @Length(max = 3_000)
    String content
) {

    public CommunityContent(String content) {
        this.content = content;
        BaemoValueObjectValidator.valid(this);
    }

    public static CommunityContent of(final String content) {
        return new CommunityContent(content);
    }
}