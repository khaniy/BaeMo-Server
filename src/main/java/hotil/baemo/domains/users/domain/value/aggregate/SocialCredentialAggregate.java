package hotil.baemo.domains.users.domain.value.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.oauth.OauthId;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record SocialCredentialAggregate(
    @NotNull
    JoinType joinType,
    @NotNull
    OauthId oauthId,
    @NotNull
    Phone phone
) {
    @Builder
    public SocialCredentialAggregate(
        JoinType joinType,
        OauthId oauthId,
        Phone phone
    ) {
        this.joinType = joinType;
        this.oauthId = oauthId;
        this.phone = phone;
        BaemoValueObjectValidator.valid(this);
    }
}
