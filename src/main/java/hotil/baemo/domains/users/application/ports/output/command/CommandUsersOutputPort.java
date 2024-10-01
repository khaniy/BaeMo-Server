package hotil.baemo.domains.users.application.ports.output.command;

import hotil.baemo.domains.users.domain.value.entity.SocialId;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.aggregate.SocialUsersAggregate;
import hotil.baemo.domains.users.domain.value.aggregate.UsersAggregate;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.information.*;
import org.springframework.web.multipart.MultipartFile;

public interface CommandUsersOutputPort {
    UsersId save(UsersAggregate usersAggregate);
    SocialId save(SocialUsersAggregate usersAggregate);

    void updatePassword(Phone phone, JoinPassword joinPassword);

    void updateProfile(UsersId usersId, Nickname nickname, RealName realName, Level level, Birth birth, Gender gender, Description description, MultipartFile profile);
}