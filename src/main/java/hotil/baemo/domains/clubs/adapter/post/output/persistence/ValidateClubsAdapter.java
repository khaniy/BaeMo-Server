package hotil.baemo.domains.clubs.adapter.post.output.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.QClubsPostEntity;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.repository.jpa.ClubsPostJpaRepository;
import hotil.baemo.domains.clubs.application.post.ports.output.query.ValidateClubsPostOutputPort;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ValidateClubsAdapter implements ValidateClubsPostOutputPort {
    private static final QClubsPostEntity POST = QClubsPostEntity.clubsPostEntity;
    private static final QClubsMemberEntity MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private final JPAQueryFactory factory;

    private final ClubsPostJpaRepository clubsPostJpaRepository;

    @Override
    public void validDetailView(ClubsUserId clubsUserId, ClubsPostId clubsPostId) {
        final var result = factory
            .select(MEMBER.clubRole)
            .from(MEMBER)
            .join(POST).on(POST.clubsPostId.eq(clubsPostId.id()))
            .where(MEMBER.clubsId.eq(POST.clubsId)
                .and(MEMBER.usersId.eq(clubsUserId.id())))
            .fetchFirst();

        if (result == null || result == ClubsRole.NON_MEMBER) {
            throw new CustomException(ResponseCode.AUTH_FAILED);
        }
    }

    @Override
    public void validUpdateAuthorization(ClubsPostId clubsPostId, ClubsUserId clubsUserId) {
        final var clubsPostEntity = clubsPostJpaRepository.loadById(clubsPostId);
        if (!Objects.equals(clubsPostEntity.getClubsPostWriter(), clubsUserId.id())) {
            throw new CustomException(ResponseCode.AUTH_FAILED);
        }
    }
}