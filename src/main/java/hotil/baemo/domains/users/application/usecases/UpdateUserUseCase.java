package hotil.baemo.domains.users.application.usecases;

import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpdateUserUseCase {

    void updateProfile(
        UsersId usersId,
        RealName realName,
        Level level,
        Gender gender,
        Description description,
        List<Location> location,
        MultipartFile profile
    );

    void updateInfo(UsersId usersId, Description description, List<Location> location, MultipartFile image);

    void updatePassword(Phone phone, JoinPassword joinPassword);
}
