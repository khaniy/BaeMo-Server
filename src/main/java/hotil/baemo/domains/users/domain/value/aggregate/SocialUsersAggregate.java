package hotil.baemo.domains.users.domain.value.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.entity.SocialId;
import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;
import lombok.Builder;

public record SocialUsersAggregate(
    SocialId socialId,
    SocialCredentialAggregate socialCredentialAggregate,
    InformationAggregate informationAggregate,
    RequiredTerms requiredTerms
) {

    @Builder
    public SocialUsersAggregate(SocialId socialId, SocialCredentialAggregate socialCredentialAggregate, InformationAggregate informationAggregate, RequiredTerms requiredTerms) {
        this.socialId = socialId;
        this.socialCredentialAggregate = socialCredentialAggregate;
        this.informationAggregate = informationAggregate;
        this.requiredTerms = requiredTerms;
        BaemoValueObjectValidator.valid(this);
    }
}