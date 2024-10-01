package hotil.baemo.domains.community.adapter.output.persistence;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CategoryEntity;
import hotil.baemo.domains.community.adapter.output.persistence.entity.CommunityImageEntity;
import hotil.baemo.domains.community.adapter.output.persistence.jpa.CommunityImageQueryRepository;
import hotil.baemo.domains.community.adapter.output.persistence.mapper.CommunityMapper;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CategoryJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityImageJpaRepository;
import hotil.baemo.domains.community.adapter.output.persistence.repository.CommunityJpaRepository;
import hotil.baemo.domains.community.adapter.output.storage.CommunityImageStorage;
import hotil.baemo.domains.community.application.ports.output.CommandCommunityOutputPort;
import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.entity.Writer;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

import static hotil.baemo.domains.community.domain.value.CommunityCategory.*;

@Service
@RequiredArgsConstructor
public class CommandCommunityOutputAdapter implements CommandCommunityOutputPort {
    private final CommunityImageStorage communityImageStorage;
    private final CommunityJpaRepository communityJpaRepository;
    private final CommunityImageJpaRepository communityImageJpaRepository;
    private final CommunityImageQueryRepository communityImageQueryRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public CommunityId save(Community community, List<MultipartFile> imageList) {
        final var saved = communityJpaRepository.save(CommunityMapper.convert(community));
        final var communityId = saved.getCommunityId();
        final var pathList = communityImageStorage.saveImage(imageList);

        pathList.forEach(e -> communityImageJpaRepository.save(CommunityImageEntity.builder()
            .image(e)
            .communityId(communityId)
            .build())
        );

        return CommunityId.of(communityId);
    }

    @Override
    public void update(Community community, List<MultipartFile> imageList) {
        final var entity = communityJpaRepository.findById(community.getCommunityId().id())
            .orElseThrow(() -> new CustomException(ResponseCode.COMMUNITY_NOT_FOUND));

        entity.updateTitle(community.getCommunityTitle().title());
        entity.updateContent(community.getCommunityContent().content());
        entity.updateCategory(community.getCommunityCategory());

        final var communityId = entity.getCommunityId();
        communityImageQueryRepository.delete(new CommunityId(communityId));

        final var pathList = communityImageStorage.saveImage(imageList);
        pathList.forEach(e -> {
            communityImageJpaRepository.save(CommunityImageEntity.builder()
                .image(e)
                .communityId(communityId)
                .build());
        });

        communityJpaRepository.save(entity);
    }

    @Override
    public void remove(CommunityId communityId, Writer writer) {
        final var entity = communityJpaRepository.findById(communityId.id())
            .orElseThrow(() -> new CustomException(ResponseCode.COMMUNITY_NOT_FOUND));

        if (!Objects.equals(entity.getWriter(), writer.id())) {
            throw new CustomException(ResponseCode.AUTH_RESTRICTED);
        }

        communityJpaRepository.deleteById(entity.getCommunityId());
    }

    @Override
    public void subscript(List<CommunityCategory> list, CommunityUserId userId) {
        list.forEach(c -> categoryJpaRepository.save(CategoryEntity.builder()
            .userId(userId.id())
            .isDaily(list.contains(DAILY))
            .isClubsPromotion(list.contains(CLUB_PROMOTION))
            .isPartnerRecruitment(list.contains(PARTNER_RECRUITMENT))
            .isExerciseRecruitment(list.contains(EXERCISE_RECRUITMENT))
            .isCompetitionNotice(list.contains(COMPETITION_NOTICE))
            .build()));
        categoryJpaRepository.deleteByUserId(userId.id());
    }
}
