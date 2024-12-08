package hotil.baemo.domains.users.adapter.output.command;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UserLocationEntity;
import hotil.baemo.domains.users.adapter.output.persistence.mapper.UsersMapper;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.SocialUserJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.UserJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.UserLocationJpaRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandUsersAdapter implements CommandUsersOutputPort {
    private final BaeMoUserJpaRepository baeMoUserJpaRepository;
    private final SocialUserJpaRepository socialUserJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserLocationJpaRepository userLocationJpaRepository;
    private final AwsS3Service awsS3Service;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsersId save(UsersAggregate user) {
        final var userEntity = usersMapper.convert(user);
        final var encode = passwordEncoder.encode(userEntity.getPassword());
        userEntity.updatePassword(encode);

        final var savedEntity = baeMoUserJpaRepository.save(userEntity);

        return new UsersId(savedEntity.getId());
    }

    @Override
    public SocialId save(SocialUsersAggregate socialUsersAggregate) {
        final var socialEntity = usersMapper.convert(socialUsersAggregate);
        final var savedEntity = socialUserJpaRepository.save(socialEntity);

        return new SocialId(savedEntity.getId());
    }

    @Override
    public void updatePassword(Phone phone, JoinPassword joinPassword) {
        final var usersEntity = baeMoUserJpaRepository.findByPhone(phone.phone())
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
        final var encode = passwordEncoder.encode(joinPassword.password());
        usersEntity.updatePassword(encode);
    }

    @Override
    public void updateProfile(
        UsersId usersId,
        RealName realName,
        Level level,
        Gender gender,
        Description description,
        List<Location> location,
        MultipartFile profile
    ) {
        final var user = userJpaRepository.findById(usersId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
        if (profile != null) {
            String url = awsS3Service.write(profile, DomainType.USER_THUMBNAIL);
            user.updateProfileImage(url);
        }
        user.updateProfile(
            realName.name(),
            level,
            gender,
            description != null ? description.description() : null
        );
        if (location != null) {
            userLocationJpaRepository.deleteByUserId(user.userId());
            userLocationJpaRepository.saveAll(location.stream().map(
                    c -> UserLocationEntity.builder()
                        .userId(user.userId())
                        .location(c.location())
                        .locationCode(Long.parseLong(c.code()))
                        .build())
                .toList()
            );
        }
    }

    @Override
    public void updateProfile(UsersId usersId, Description description, List<Location> location, MultipartFile profile) {
        final var user = userJpaRepository.findById(usersId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.USERS_NOT_FOUND));
        if (profile != null) {
            String url = awsS3Service.write(profile, DomainType.USER_THUMBNAIL);
            user.updateProfileImage(url);
        }
        user.updateProfile(description.description());
        if (location != null) {
            userLocationJpaRepository.deleteByUserId(user.userId());
            userLocationJpaRepository.saveAll(location.stream().map(
                    c -> UserLocationEntity.builder()
                        .userId(user.userId())
                        .location(c.location())
                        .locationCode(Long.parseLong(c.code()))
                        .build())
                .toList()
            );
        }
    }
}