package hotil.baemo.domains.notice.adapter.input.rest.dto;

import hotil.baemo.core.aws.value.PreSignedUrl;
import lombok.Builder;

import java.util.List;

public interface NoticeResponseDTO {
    @Builder
    record PreSignedUrls(
        List<PreSignedUrl.Put> urls
    ) implements NoticeResponseDTO {
    }
}
