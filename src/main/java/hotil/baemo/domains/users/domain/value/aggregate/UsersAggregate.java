package hotil.baemo.domains.users.domain.value.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;
import lombok.Builder;


public record UsersAggregate(
    UsersId usersId,
    CredentialAggregate credentialAggregate,
    InformationAggregate informationAggregate,
    RequiredTerms requiredTerms
) {
    @Builder
    public UsersAggregate(
        UsersId usersId,
        CredentialAggregate credentialAggregate,
        InformationAggregate informationAggregate,
        RequiredTerms requiredTerms
    ) {
        this.usersId = usersId;
        this.credentialAggregate = credentialAggregate;
        this.informationAggregate = informationAggregate;
        this.requiredTerms = requiredTerms;
        BaemoValueObjectValidator.valid(this);
    }
}