package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.entity.SocialId;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.information.Birth;
import hotil.baemo.domains.users.domain.value.information.Gender;
import hotil.baemo.domains.users.domain.value.information.Level;
import hotil.baemo.domains.users.domain.value.information.RealName;
import hotil.baemo.domains.users.domain.value.oauth.OauthId;
import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;

public interface JoinUserUseCase {
    UsersId baemoJoin(JoinType joinType, Phone phone, JoinPassword joinPassword, RealName realName, Level level, Gender gender, RequiredTerms requiredTerms);
    SocialId socialJoin(JoinType joinType, OauthId oauthId, Phone phone, RealName realName, Level level, Gender gender, RequiredTerms requiredTerms);
}
