package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.core.util.BaeMoOAuth2UserNameProvider;
import hotil.baemo.domains.users.application.ports.output.AlreadyBaeMoUserOutputPort;
import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.application.ports.output.command.CommandUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.JoinUserUseCase;
import hotil.baemo.domains.users.domain.policy.BaemoCodeGenerator;
import hotil.baemo.domains.users.domain.value.aggregate.*;
import hotil.baemo.domains.users.domain.value.credential.JoinPassword;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.entity.SocialId;
import hotil.baemo.domains.users.domain.value.entity.UsersId;
import hotil.baemo.domains.users.domain.value.information.*;
import hotil.baemo.domains.users.domain.value.oauth.OauthId;
import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JoinUserInputPort implements JoinUserUseCase {
    private final BaemoCodeGenerator baemoCodeGenerator;
    private final ValidUsersOutputPort validUsersOutputPort;
    private final CommandUsersOutputPort commandUsersOutputPort;
    private final AlreadyBaeMoUserOutputPort alreadyBaeMoUserOutputPort;

    @Override
    public UsersId baemoJoin(
        JoinType joinType, Phone phone,
        JoinPassword joinPassword, RealName realName, Level level,
        Gender gender, RequiredTerms requiredTerms
    ) {
        validUsersOutputPort.validAuthenticated(phone);
        alreadyBaeMoUserOutputPort.valid(phone);

        final var baemoCode = baemoCodeGenerator.generate();

        final var usersId = commandUsersOutputPort.save(UsersAggregate.builder()

            .credentialAggregate(CredentialAggregate.builder()
                .joinPassword(joinPassword)
                .joinType(joinType)
                .phone(phone)
                .build())

            .informationAggregate(InformationAggregate.builder()
                .baemoCode(baemoCode)
                .nickname(new Nickname(BaeMoOAuth2UserNameProvider.getNickname()))
                .realName(realName)
                .level(level)
                .birth(null)
                .gender(gender)
                .build())

            .requiredTerms(requiredTerms)
            .build());

        validUsersOutputPort.removeAuthenticated(phone);
        return usersId;
    }

    @Override
    public SocialId socialJoin(JoinType joinType, OauthId oauthId, Phone phone, RealName realName, Level level, Gender gender, RequiredTerms requiredTerms) {
        alreadyBaeMoUserOutputPort.valid(phone);

        final var baemoCode = baemoCodeGenerator.generate();
        final var usersId = commandUsersOutputPort.save(SocialUsersAggregate.builder()

            .socialCredentialAggregate(SocialCredentialAggregate.builder()
                .joinType(joinType)
                .oauthId(oauthId)
                .phone(phone)
                .build())

            .informationAggregate(InformationAggregate.builder()
                .baemoCode(baemoCode)
                .nickname(new Nickname(BaeMoOAuth2UserNameProvider.getNickname()))
                .realName(realName)
                .level(level)
                .birth(null)
                .gender(gender)
                .build())

            .requiredTerms(requiredTerms)
            .build());

//        validUsersOutputPort.removeAuthenticated(phone);
        return usersId;
    }
}