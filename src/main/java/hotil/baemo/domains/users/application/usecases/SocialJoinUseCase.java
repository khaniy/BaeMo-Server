package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.entity.SocialId;
import hotil.baemo.domains.users.domain.value.information.Birth;
import hotil.baemo.domains.users.domain.value.information.Gender;
import hotil.baemo.domains.users.domain.value.information.Level;
import hotil.baemo.domains.users.domain.value.information.RealName;
import hotil.baemo.domains.users.domain.value.oauth.OauthId;
import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;

public interface SocialJoinUseCase {
    SocialId join(JoinType joinType, OauthId oauthId, Phone phone, RealName realName, Level level, Birth birth, Gender gender, RequiredTerms requiredTerms);
}
