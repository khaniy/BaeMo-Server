package hotil.baemo.domains.users.application.ports.output;

import hotil.baemo.domains.users.application.dto.QUserProfileDTO;
import hotil.baemo.domains.users.domain.value.entity.UsersId;

public interface RetrieveUsersProfileOutputPort {
    QUserProfileDTO.UserProfile getUserProfile(UsersId usersId, UsersId targetUsersId);

    QUserProfileDTO.MyProfile getMyProfile(UsersId usersId);
}
