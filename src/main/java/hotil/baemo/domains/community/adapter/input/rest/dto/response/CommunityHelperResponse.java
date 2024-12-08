package hotil.baemo.domains.community.adapter.input.rest.dto.response;

import hotil.baemo.core.aws.value.PreSignedUrl;
import lombok.Builder;

import java.util.List;

public interface CommunityHelperResponse {
    @Builder
    record PutUrlList(
        List<PreSignedUrl.Put> list
    ) implements CommunityHelperResponse {
    }
}
