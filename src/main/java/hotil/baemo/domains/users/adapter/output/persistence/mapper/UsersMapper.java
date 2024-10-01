package hotil.baemo.domains.users.adapter.output.persistence.mapper;

import hotil.baemo.domains.users.adapter.output.persistence.entity.SocialEntity;
import hotil.baemo.domains.users.adapter.output.persistence.entity.UsersEntity;
import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.domains.users.domain.value.aggregate.SocialUsersAggregate;
import hotil.baemo.domains.users.domain.value.aggregate.UsersAggregate;
import org.springframework.stereotype.Service;

@Service
public class UsersMapper {

    public UsersAggregate convert(final UsersEntity entity) {
        return UsersAggregate.builder()
            .build();
    }

    public BaeMoUserEntity convert(final UsersAggregate domain) {
        return BaeMoUserEntity.builder()
            .id(domain.usersId() != null ? domain.usersId().id() : null)

            .phone(domain.credentialAggregate().phone().phone())
            .baemoCode(domain.informationAggregate().baemoCode().code())
            .nickname(domain.informationAggregate().nickname().name())
            .realName(domain.informationAggregate().realName().name())
            .level(domain.informationAggregate().level())

            .birth(domain.informationAggregate().birth() != null ? domain.informationAggregate().birth().birth() : null)
            .gender(domain.informationAggregate().gender() != null ? domain.informationAggregate().gender() : null)

            .requiredTerms(domain.requiredTerms().isAgree())

            .password(domain.credentialAggregate().joinPassword().password())

            .build();
    }

    public SocialEntity convert(final SocialUsersAggregate domain) {
        return SocialEntity.builder()
            .id(domain.socialId() != null ? domain.socialId().id() : null)
            .joinType(domain.socialCredentialAggregate().joinType())
            .phone(domain.socialCredentialAggregate().phone().phone())

            .baemoCode(domain.informationAggregate().baemoCode().code())
            .nickname(domain.informationAggregate().nickname().name())
            .realName(domain.informationAggregate().realName().name())
            .level(domain.informationAggregate().level())

            .birth(domain.informationAggregate().birth() != null ? domain.informationAggregate().birth().birth() : null)
            .gender(domain.informationAggregate().gender() != null ? domain.informationAggregate().gender() : null)

            .requiredTerms(domain.requiredTerms().isAgree())

            .oauthId(domain.socialCredentialAggregate().oauthId().id())
            .build();
    }
}