package hotil.baemo.domains.clubs.adapter.post.input.dto.request;

import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.post.aggregate.ClubsPostImageAggregate;
import hotil.baemo.domains.clubs.domain.post.aggregate.ClubsPostImageAggregateList;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImageOrder;
import hotil.baemo.domains.clubs.domain.post.value.images.ClubsPostImagePath;
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
        ClubsPostType type,
        ImageDTOList imageDTOList
    ) implements ClubsPostRequest {

        public ClubsId toClubsId() {
            return new ClubsId(this.clubsId);
        }

        public ClubsPostTitle toClubsPostTitle() {
            return new ClubsPostTitle(this.title);
        }

        public ClubsPostContent toClubsPostContent() {
            return new ClubsPostContent(this.content);
        }

        public ClubsPostImageAggregateList toClubsPostImageAggregateList() {
            return ClubsPostImageAggregateList.builder()
                .list(this.imageDTOList.list.stream()
                    .map(e -> ClubsPostImageAggregate.builder()
                        .clubsPostImagePath(e.toClubsPostImagePath())
                        .clubsPostImageOrder(e.toClubsPostImageOrder())
                        .isThumbnail(e.isThumbnail)
                        .build())
                    .toList())
                .build();
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
        public ClubsPostImagePath toClubsPostImagePath() {
            return new ClubsPostImagePath(this.path);
        }

        public ClubsPostImageOrder toClubsPostImageOrder() {
            return new ClubsPostImageOrder(this.order);
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
        ClubsPostType type,
        ImageDTOList imageDTOList
    ) implements ClubsPostRequest {
        public ClubsPostId toClubsPostId() {
            return new ClubsPostId(this.clubsPostId);
        }

        public ClubsPostTitle toClubsPostTitle() {
            return new ClubsPostTitle(this.title);
        }

        public ClubsPostContent toClubsPostContent() {
            return new ClubsPostContent(this.content);
        }

        public ClubsPostImageAggregateList toClubsPostImageAggregateList() {
            return ClubsPostImageAggregateList.builder()
                .list(this.imageDTOList.list.stream()
                    .map(e -> ClubsPostImageAggregate.builder()
                        .clubsPostImagePath(e.toClubsPostImagePath())
                        .clubsPostImageOrder(e.toClubsPostImageOrder())
                        .isThumbnail(e.isThumbnail)
                        .build())
                    .toList())
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
