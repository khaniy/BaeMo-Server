package hotil.baemo.domains.users.adapter.output;

import hotil.baemo.domains.users.adapter.output.persistence.repository.RetrieveUserProfileQRepository;
import hotil.baemo.domains.users.application.dto.QUserProfileDTO;
import hotil.baemo.domains.users.application.ports.output.RetrieveUsersProfileOutputPort;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveUserProfileAdapter implements RetrieveUsersProfileOutputPort {

    private final RetrieveUserProfileQRepository retrieveUserProfileQRepository;

    @Override
    public QUserProfileDTO.UserProfile getUserProfile(UsersId usersId, UsersId targetUsersId) {
        return retrieveUserProfileQRepository.findUserProfile(usersId.id(), targetUsersId.id());
    }

    @Override
    public QUserProfileDTO.MyProfile getMyProfile(UsersId usersId) {
        return retrieveUserProfileQRepository.findMyProfile(usersId.id());
    }
}
