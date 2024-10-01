package hotil.baemo.domains.notice.adapter.input.rest.dto;

import hotil.baemo.domains.clubs.domain.post.aggregate.ClubsPostImageAggregate;
import hotil.baemo.domains.clubs.domain.post.aggregate.ClubsPostImageAggregateList;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostId;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostContent;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostTitle;
import hotil.baemo.domains.clubs.domain.post.value.ClubsPostType;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImage;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImages;
import hotil.baemo.domains.notice.domain.value.image.NoticeImageOrder;
import hotil.baemo.domains.notice.domain.value.image.NoticeImagePath;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

public interface NoticeRequestDTO {
    @Builder
    record Create(
        @NotBlank(message = "제목은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 200, message = "제목은 최소 1글자 최대 200 글자 이하입니다.")
        String title,
        @NotBlank(message = "내용은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 30_000, message = "내용은 최소 1글자 최대 30,000 글자 이하입니다.")
        String content,
        List<Image> images
    ) implements NoticeRequestDTO {

        public NoticeTitle toNoticeTitle() {
            return new NoticeTitle(this.title);
        }

        public NoticeContent toNoticeContent() {
            return new NoticeContent(this.content);
        }

        public NoticeImages toNoticeImages() {
            return NoticeImages.builder()
                .imageList(this.images.stream()
                    .map(Image::toNoticeImage)
                    .collect(Collectors.toList())
                )
                .build();
        }
    }

    @Builder
    record Image(
        @NotBlank(message = "이미지 Path 는 필수 값입니다.")
        String path,
        @NotNull(message = "이미지 순서는 필수 값입니다.")
        @Positive(message = "이미지 순서는 필수 값입니다.")
        Long order,
        @NotNull(message = "썸네일 여부는 필수 값입니다.")
        Boolean isThumbnail
    ) implements NoticeRequestDTO {

        public static NoticeImage toNoticeImage(Image image) {
            return NoticeImage.builder()
                .path(new NoticeImagePath(image.path()))
                .order(new NoticeImageOrder(image.order()))
                .isThumbnail(image.isThumbnail)
                .build();
        }
    }

    @Builder
    record Update(
        @NotBlank(message = "제목은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 200, message = "제목은 최소 1글자 최대 200 글자 이하입니다.")
        String title,
        @NotBlank(message = "내용은 공백이 될 수 없습니다.")
        @Size(min = 1, max = 30_000, message = "내용은 최소 1글자 최대 30,000 글자 이하입니다.")
        String content,
        List<Image> images
    ) implements NoticeRequestDTO {

        public NoticeTitle toNoticeTitle() {
            return new NoticeTitle(this.title);
        }

        public NoticeContent toNoticeContent() {
            return new NoticeContent(this.content);
        }

        public NoticeImages toNoticeImages() {
            return NoticeImages.builder()
                .imageList(this.images.stream()
                    .map(Image::toNoticeImage)
                    .collect(Collectors.toList())
                )
                .build();
        }
    }

    @Builder
    record GetPreSignedUrl(
        @Positive(message = "0보다 커야합니다.")
        @Max(message = "올릴수 있는 게시글은 최대 10개입니다", value = 10)
        Integer count
    ) implements NoticeRequestDTO {
    }
}
