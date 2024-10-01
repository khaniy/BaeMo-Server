package hotil.baemo.domains.users.application.ports.input;

import hotil.baemo.core.util.BaeMoOAuth2UserNameProvider;
import hotil.baemo.domains.users.application.ports.output.AlreadyBaeMoUserOutputPort;
import hotil.baemo.domains.users.application.ports.output.ValidUsersOutputPort;
import hotil.baemo.domains.users.application.ports.output.command.CommandUsersOutputPort;
import hotil.baemo.domains.users.application.usecases.SocialJoinUseCase;
import hotil.baemo.domains.users.domain.policy.BaemoCodeGenerator;
import hotil.baemo.domains.users.domain.value.aggregate.InformationAggregate;
import hotil.baemo.domains.users.domain.value.aggregate.SocialCredentialAggregate;
import hotil.baemo.domains.users.domain.value.aggregate.SocialUsersAggregate;
import hotil.baemo.domains.users.domain.value.credential.JoinType;
import hotil.baemo.domains.users.domain.value.credential.Phone;
import hotil.baemo.domains.users.domain.value.entity.SocialId;
import hotil.baemo.domains.users.domain.value.information.*;
import hotil.baemo.domains.users.domain.value.oauth.OauthId;
import hotil.baemo.domains.users.domain.value.terms.RequiredTerms;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SocialJoinInputPort implements SocialJoinUseCase {
    private final BaemoCodeGenerator baemoCodeGenerator;
    private final ValidUsersOutputPort validUsersOutputPort;
    private final CommandUsersOutputPort commandUsersOutputPort;
    private final AlreadyBaeMoUserOutputPort alreadyBaeMoUserOutputPort;

    @Override
    public SocialId join(
        JoinType joinType, OauthId oauthId,
        Phone phone, RealName realName, Level level,
        Birth birth, Gender gender, RequiredTerms requiredTerms
    ) {
        validUsersOutputPort.validAuthenticated(phone);
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
                .birth(birth)
                .gender(gender)
                .build())

            .requiredTerms(requiredTerms)
            .build());

        validUsersOutputPort.removeAuthenticated(phone);
        return usersId;
    }
}