package hotil.baemo.domains.users.application.dto;

import hotil.baemo.domains.users.domain.value.information.Level;
import lombok.Builder;

public interface QUserProfileDTO {

    @Builder
    record MyProfile(
        Long userId,
        String realName,
        String nickName,
        Level level,
//    String location,
        String baemoCode,
        String description,
        String profileUrl
    ) implements QUserProfileDTO {
    }

    @Builder
    record UserProfile(
        Long userId,
        String realName,
        Level level,
//    String location,
        String description,
        String profileUrl,
        boolean isFriend
    ) implements QUserProfileDTO {
    }
    @Builder
    record UserProfileInfo(
        Long userId,
        String realName,
        Level level,
//    String location,
        String description,
        String profileUrl
    ) implements QUserProfileDTO {
    }
}

