package hotil.baemo.domains.users.domain.value.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.LoginPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record CredentialAggregate(
    JoinPassword joinPassword,
    @NotNull
    JoinType joinType,
    LoginPassword loginPassword,
    @NotNull
    Phone phone
) {
    @Builder
    public CredentialAggregate(
        JoinPassword joinPassword,
        JoinType joinType,
        LoginPassword loginPassword,
        Phone phone
    ) {
        this.joinPassword = joinPassword;
        this.joinType = joinType;
        this.loginPassword = loginPassword;
        this.phone = phone;
        BaemoValueObjectValidator.valid(this);
    }
}
