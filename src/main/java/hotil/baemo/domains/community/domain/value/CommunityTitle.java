package hotil.baemo.domains.community.domain.value;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CommunityTitle(
    @NotBlank
    @Length(max = 500)
    String title
) {

    public CommunityTitle(String title) {
        this.title = title;
        BaemoValueObjectValidator.valid(this);
    }

    public static CommunityTitle of(final String title) {
        return new CommunityTitle(title);
    }
}
