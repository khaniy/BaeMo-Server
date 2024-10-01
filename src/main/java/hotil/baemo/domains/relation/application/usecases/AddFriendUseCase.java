package hotil.baemo.domains.relation.application.usecases;

import hotil.baemo.domains.relation.domain.value.UserCode;
import hotil.baemo.domains.relation.domain.value.UserId;
import hotil.baemo.domains.relation.domain.value.UserName;

public interface AddFriendUseCase {

    void addFriendById(UserId userId, UserId targetId);

    void addFriendByCode(UserId userId, UserCode userCode, UserName userName);

}
