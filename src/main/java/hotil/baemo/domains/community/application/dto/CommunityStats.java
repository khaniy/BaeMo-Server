package hotil.baemo.domains.community.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommunityStats {
    private final int viewCount;
    private final int likeCount;
    private final int commentCount;
}
