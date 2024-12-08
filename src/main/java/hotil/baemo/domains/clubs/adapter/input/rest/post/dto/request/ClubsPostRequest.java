package hotil.baemo.domains.clubs.adapter.input.rest.post.dto.request;

import hotil.baemo.domains.clubs.domain.entity.club.ClubId;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostImageVOGroup;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostImagesVOGroup;
import hotil.baemo.domains.clubs.domain.aggregate.ClubPostVOGroup;
import hotil.baemo.domains.clubs.domain.entity.post.ClubPostId;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostContent;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostTitle;
import hotil.baemo.domains.clubs.domain.value.post.ClubPostType;
import hotil.baemo.domains.clubs.domain.value.post.images.ClubPostImageOrder;
import hotil.baemo.domains.clubs.domain.value.post.images.ClubPostImagePath;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;

public interface ClubsPostRequest {
    @Builder
    record CreateDTO(
        @NotNull(message = "모임 아이디가 비어있습니다.")
        @Positive(message = "모임 아이디가 잘못되었습니다.")
        Long clubsId,
        @NotBlank(message = "제목은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 200, message = "제목은 최소 1글자 최대 200 글자 이하입니다.")
        String title,
        @NotBlank(message = "내용은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 30_000, message = "내용은 최소 1글자 최대 30,000 글자 이하입니다.")
        String content,
        @NotNull(message = "모임 게시글의 카테고리가 비었습니다.")
        ClubPostType type,
        ImageDTOList imageDTOList
    ) implements ClubsPostRequest {

        public ClubPostVOGroup toVOGroup() {
            return ClubPostVOGroup.builder()
                .clubPostTitle(new ClubPostTitle(title))
                .clubPostContent(new ClubPostContent(content))
                .clubPostType(type)
                .clubPostImagesVOGroup(new ClubPostImagesVOGroup(this.imageDTOList.list.stream()
                    .map(e -> ClubPostImageVOGroup.builder()
                        .clubPostImagePath(e.toClubsPostImagePath())
                        .clubPostImageOrder(e.toClubsPostImageOrder())
                        .isThumbnail(e.isThumbnail)
                        .build())
                    .toList())
                )
                .build();
        }

        public ClubId toClubId() {
            return new ClubId(this.clubsId);
        }

    }

    @Builder
    record ImageDTOList(
        List<ImageDetailsDTO> list
    ) implements ClubsPostRequest {
    }

    @Builder
    record ImageDetailsDTO(
        @NotBlank(message = "이미지 Path 는 필수 값입니다.")
        String path,
        @NotNull(message = "이미지 순서는 필수 값입니다.")
        @Positive(message = "이미지 순서는 필수 값입니다.")
        Long order,
        @NotNull(message = "썸네일 여부는 필수 값입니다.")
        Boolean isThumbnail
    ) implements ClubsPostRequest {
        public ClubPostImagePath toClubsPostImagePath() {
            return new ClubPostImagePath(this.path);
        }

        public ClubPostImageOrder toClubsPostImageOrder() {
            return new ClubPostImageOrder(this.order);
        }
    }

    @Builder
    record UpdateDTO(
        @NotNull(message = "모임의 게시글 아이디가 비어있습니다.")
        @Positive(message = "모임의 게시글 아이디가 잘못되었습니다.")
        Long clubsPostId,
        @NotBlank(message = "제목은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 200, message = "제목은 최소 1글자 최대 200 글자 이하입니다.")
        String title,
        @NotBlank(message = "내용은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 30_000, message = "내용은 최소 1글자 최대 30,000 글자 이하입니다.")
        String content,
        @NotNull(message = "모임 게시글의 카테고리가 비었습니다.")
        ClubPostType type,
        ImageDTOList imageDTOList
    ) implements ClubsPostRequest {
        public ClubPostId toClubsPostId() {
            return new ClubPostId(this.clubsPostId);
        }

        public ClubPostTitle toClubsPostTitle() {
            return new ClubPostTitle(this.title);
        }

        public ClubPostContent toClubsPostContent() {
            return new ClubPostContent(this.content);
        }

        public ClubPostVOGroup toVOGroup() {
            return ClubPostVOGroup.builder()
                .clubPostTitle(new ClubPostTitle(title))
                .clubPostContent(new ClubPostContent(content))
                .clubPostType(type)
                .clubPostImagesVOGroup(new ClubPostImagesVOGroup(this.imageDTOList.list.stream()
                    .map(e -> ClubPostImageVOGroup.builder()
                        .clubPostImagePath(e.toClubsPostImagePath())
                        .clubPostImageOrder(e.toClubsPostImageOrder())
                        .isThumbnail(e.isThumbnail)
                        .build())
                    .toList())
                )
                .build();
        }
    }


    @Builder
    record GetPreSignedUrl(
        @Positive(message = "0보다 커야합니다.")
        @Max(message = "올릴수 있는 게시글은 최대 10개입니다", value = 10)
        Integer count
    ) implements ClubsPostRequest {
    }
}

