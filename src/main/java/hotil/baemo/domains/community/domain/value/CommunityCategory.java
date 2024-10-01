package hotil.baemo.domains.community.domain.value;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum CommunityCategory {
    DAILY("일상"),
    EXERCISE_RECRUITMENT("운동 모집"),
    CLUB_PROMOTION("모임 홍보"),
    PARTNER_RECRUITMENT("파트너 모집"),
    COMPETITION_NOTICE("대회 공지"),
    ;

    private final String description;

    public static List<String> getAllList() {
        return Arrays.stream(CommunityCategory.values())
            .map(CommunityCategory::getDescription)
            .toList();
    }

    public static CommunityCategory convertDescription(String description) {
        return Arrays.stream(CommunityCategory.values())
            .filter(e -> e.description.equals(description))
            .findFirst()
            .orElseThrow(() -> new CustomException(ResponseCode.COMMUNITY_CATEGORY_NOT_FOUND));
    }
}