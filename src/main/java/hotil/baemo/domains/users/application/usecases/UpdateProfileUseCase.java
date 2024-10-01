package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.*;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfileUseCase {

    void updateProfile(UsersId usersId, Nickname nickname, RealName realName, Level level, Birth birth, Gender gender, Description description, MultipartFile profile);
}
