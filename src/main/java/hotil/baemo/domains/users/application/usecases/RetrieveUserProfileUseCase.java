package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.application.dto.QUserProfileDTO;
import hotil.baemo.domains.users.domain.value.entity.UsersId;

public interface RetrieveUserProfileUseCase {
    QUserProfileDTO.UserProfile retrieveUserProfile(UsersId usersId, UsersId targetUsersId);

    QUserProfileDTO.MyProfile retrieveMyProfile(UsersId id);
}
