package hotil.baemo.domains.users.domain.value.aggregate;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import hotil.baemo.domains.users.domain.value.entity.BaemoCode;
import hotil.baemo.domains.users.domain.value.information.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public record InformationAggregate(
    @NotNull
    BaemoCode baemoCode,
    @NotNull
    Nickname nickname,
    @NotNull
    RealName realName,
    @NotNull
    Level level,

    Birth birth,

    Gender gender
) {
    @Builder
    public InformationAggregate(
        BaemoCode baemoCode,
        Nickname nickname,
        RealName realName,
        Level level,
        Birth birth,
        Gender gender
    ) {
        this.baemoCode = baemoCode;
        this.nickname = nickname;
        this.realName = realName;
        this.level = level;
        this.birth = birth;
        this.gender = gender;
        BaemoValueObjectValidator.valid(this);
    }
}