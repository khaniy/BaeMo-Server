package hotil.baemo.domains.users.adapter.output.command;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.persistence.mapper.UsersMapper;
import hotil.baemo.domains.users.adapter.output.persistence.repository.AbstractBaeMoUsersEntityJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserEntityJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.SocialJpaRepository;
import hotil.baemo.domains.users.application.ports.output.command.CommandUsersOutputPort;
import hotil.baemo.domains.users.domain.value.aggregate.SocialUsersAggregate;
import hotil.baemo.domains.users.domain.value.aggregate.UsersAggregate;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.entity.SocialId;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommandUsersAdapter implements CommandUsersOutputPort {
    private final BaeMoUserEntityJpaRepository baeMoUserEntityJpaRepository;
    private final SocialJpaRepository socialJpaRepository;
    private final AbstractBaeMoUsersEntityJpaRepository abstractBaeMoUsersEntityJpaRepository;
    private final AwsS3Service awsS3Service;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsersId save(UsersAggregate user) {
        final var userEntity = usersMapper.convert(user);
        final var encode = passwordEncoder.encode(userEntity.getPassword());
        userEntity.updatePassword(encode);

        final var savedEntity = baeMoUserEntityJpaRepository.save(userEntity);

        return new UsersId(savedEntity.getId());
    }

    @Override
    public SocialId save(SocialUsersAggregate socialUsersAggregate) {
        final var socialEntity = usersMapper.convert(socialUsersAggregate);
        final var savedEntity = socialJpaRepository.save(socialEntity);

        return new SocialId(savedEntity.getId());
    }

    @Override
    public void updatePassword(Phone phone, JoinPassword joinPassword) {
        final var usersEntity = baeMoUserEntityJpaRepository.findByPhone(phone.phone())
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
        final var encode = passwordEncoder.encode(joinPassword.password());
        usersEntity.updatePassword(encode);
    }

    @Override
    public void updateProfile(
        UsersId usersId,
        Nickname nickname,
        RealName realName,
        Level level,
        Birth birth,
        Gender gender,
        Description description,
        MultipartFile profile
    ) {
        final var user = abstractBaeMoUsersEntityJpaRepository.findById(usersId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
        if (profile != null) {
            String url = awsS3Service.write(profile, DomainType.USERS);
            user.updateProfileImage(url);
        }
        user.updateProfile(
            nickname.name(),
            realName.name(),
            level,
            birth != null ? birth.birth() : null,
            gender,
            description != null ? description.description() : null
        );
    }
}