package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.domains.users.application.dto.QUserProfileDTO;
import hotil.baemo.domains.users.application.ports.output.RetrieveUsersProfileOutputPort;
import hotil.baemo.domains.users.application.usecases.RetrieveUserProfileUseCase;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveUserProfileInputPort implements RetrieveUserProfileUseCase {

    private final RetrieveUsersProfileOutputPort retrieveUsersProfileOutputPort;

    @Override
    public QUserProfileDTO.UserProfile retrieveUserProfile(UsersId usersId, UsersId targetUsersId) {
        return retrieveUsersProfileOutputPort.getUserProfile(usersId, targetUsersId);
    }

    @Override
    public QUserProfileDTO.MyProfile retrieveMyProfile(UsersId id) {
        return retrieveUsersProfileOutputPort.getMyProfile(id);
    }
}
