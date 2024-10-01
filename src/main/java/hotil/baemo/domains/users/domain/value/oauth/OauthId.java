package hotil.baemo.domains.users.domain.value.oauth;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotBlank;

public record OauthId(
    @NotBlank
    String id
) {
    public OauthId(String id) {
        this.id = id;
        BaemoValueObjectValidator.valid(this);
    }
}
