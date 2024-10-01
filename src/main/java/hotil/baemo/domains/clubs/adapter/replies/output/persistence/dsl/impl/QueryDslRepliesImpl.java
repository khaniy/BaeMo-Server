package hotil.baemo.domains.clubs.adapter.replies.output.persistence.dsl.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.QClubsMemberEntity;
import hotil.baemo.domains.clubs.adapter.post.output.persistence.entity.QClubsPostEntity;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.dsl.QueryDslReplies;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.dto.RepliesUserDTO;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.QRepliesEntity;
import hotil.baemo.domains.clubs.adapter.replies.output.persistence.entity.QStatRepliesEntity;
import hotil.baemo.domains.clubs.application.replies.dto.RetrieveRepliesDTO;
import hotil.baemo.domains.clubs.domain.clubs.entity.*;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsRole;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesPostId;
import hotil.baemo.domains.clubs.domain.replies.entity.RepliesWriter;
import hotil.baemo.domains.users.adapter.output.persistence.entity.QAbstractBaeMoUsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryDslRepliesImpl implements QueryDslReplies {
    private static final QRepliesEntity REPLIES = QRepliesEntity.repliesEntity;
    private static final QStatRepliesEntity REPLIES_STAT = QStatRepliesEntity.statRepliesEntity;
    private static final QClubsMemberEntity CLUBS_MEMBER = QClubsMemberEntity.clubsMemberEntity;
    private static final QClubsPostEntity POST = QClubsPostEntity.clubsPostEntity;
    private static final QAbstractBaeMoUsersEntity USER = QAbstractBaeMoUsersEntity.abstractBaeMoUsersEntity;
    private final JPAQueryFactory factory;

    @Override
    public ClubsUser loadClubsUser(RepliesPostId repliesPostId, RepliesWriter repliesWriter) {
        final var clubsId = factory
            .select(POST.clubsId)
            .from(POST)
            .where(POST.clubsPostId.eq(repliesPostId.id()))
            .fetchFirst();

        final var result = factory
            .select(
                CLUBS_MEMBER.usersId,
                CLUBS_MEMBER.clubsId,
                CLUBS_MEMBER.clubRole
            )
            .from(CLUBS_MEMBER)
            .join(POST).on(POST.clubsPostId.eq(repliesPostId.id()))
            .where(CLUBS_MEMBER.usersId.eq(repliesWriter.id()).and(CLUBS_MEMBER.clubsId.eq(clubsId)))
            .fetchFirst();

        if (result == null) {
            return ClubsNonMember.builder()
                .clubsId(new ClubsId(clubsId))
                .clubsUserId(new ClubsUserId(repliesWriter.id()))
                .build();
        }

        final var clubsRole = result.get(CLUBS_MEMBER.clubRole);

        return convertClubsUser(result, clubsRole);
    }

    @Override
    public ClubsUser loadClubsUser(RepliesId repliesId, RepliesWriter repliesWriter) {
        final var postId = factory
            .select(
                REPLIES.repliesPostId
            )
            .from(REPLIES)
            .where(REPLIES.repliesId.eq(repliesId.id()))
            .fetchFirst();

        final var clubsId = factory
            .select(POST.clubsId)
            .from(POST)
            .where(POST.clubsPostId.eq(postId))
            .fetchFirst();

        final var result = factory.select(
                CLUBS_MEMBER.usersId,
                CLUBS_MEMBER.clubsId,
                CLUBS_MEMBER.clubRole
            )
            .from(CLUBS_MEMBER)
            .join(REPLIES).on(REPLIES.repliesPostId.eq(repliesId.id()))
            .where(CLUBS_MEMBER.usersId.eq(repliesWriter.id()))
            .fetchFirst();

        if (result == null) {
            return ClubsNonMember.builder()
                .clubsId(new ClubsId(clubsId))
                .clubsUserId(new ClubsUserId(repliesWriter.id()))
                .build();
        }

        final var clubsRole = result.get(CLUBS_MEMBER.clubRole);

        return convertClubsUser(result, clubsRole);
    }

    @Override
    public RepliesUserDTO.SimpleInformationDTO loadUserSimpleInformation(final Long repliesWriter) {
        return factory.select(Projections.constructor(RepliesUserDTO.SimpleInformationDTO.class,
                USER.nickname,
                USER.profileImage
            ))
            .from(USER)
            .where(USER.id.eq(repliesWriter))
            .fetchFirst();
    }

    @Override
    public RetrieveRepliesDTO.RepliesDetailList loadRepliesDetailList(RepliesPostId repliesPostId) {
        final var result = factory
            .select(
                Projections.constructor(RetrieveRepliesDTO.RepliesDetail.class,
                    USER.id.as("writerId"),
                    USER.nickname.as("writerName"),
                    USER.profileImage.as("writerThumbnail"),
                    REPLIES.repliesId.as("repliesId"),
                    REPLIES.repliesContent.as("content"),
                    REPLIES.preRepliesId.as("preReplies"),
                    REPLIES_STAT.count().as("likeCount")
                )
            )
            .from(REPLIES)
            .join(USER).on(USER.id.eq(REPLIES.repliesWriter))
            .leftJoin(REPLIES_STAT).on(REPLIES_STAT.repliesId.eq(REPLIES.repliesId))
            .where(REPLIES.repliesPostId.eq(repliesPostId.id()))
            .groupBy(
                USER.id,
                USER.nickname,
                USER.profileImage,
                REPLIES.repliesId,
                REPLIES.repliesContent,
                REPLIES.preRepliesId
            )
            .fetch();

        return RetrieveRepliesDTO.RepliesDetailList.builder()
            .list(result)
            .build();
    }

    private static ClubsUser convertClubsUser(final Tuple result, final ClubsRole clubsRole) {
        if (clubsRole == ClubsRole.ADMIN) {
            return ClubsAdmin.builder()
                .clubsId(new ClubsId(result.get(CLUBS_MEMBER.clubsId)))
                .clubsUserId(new ClubsUserId(result.get(CLUBS_MEMBER.usersId)))
                .build();
        }

        if (clubsRole == ClubsRole.MANAGER) {
            return ClubsManager.builder()
                .clubsId(new ClubsId(result.get(CLUBS_MEMBER.clubsId)))
                .clubsUserId(new ClubsUserId(result.get(CLUBS_MEMBER.usersId)))
                .build();
        }

        if (clubsRole == ClubsRole.MEMBER) {
            return ClubsMember.builder()
                .clubsId(new ClubsId(result.get(CLUBS_MEMBER.clubsId)))
                .clubsUserId(new ClubsUserId(result.get(CLUBS_MEMBER.usersId)))
                .build();
        }

        throw new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER);
    }
}