package hotil.baemo.domains.clubs.adapter.clubs.output.persistence.mapper;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.entity.ClubsMemberEntity;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClubsMemberMapper {

    public static ClubsMemberEntity convert(ClubsUser domain) {
        return ClubsMemberEntity.builder()
                .clubsId(domain.getClubsId().clubsId())
                .usersId(domain.getClubsUserId().id())
                .clubRole(domain.getRole())
                .build();
    }

    public static ClubsUser convert(ClubsMemberEntity entity) {
        return Arrays.stream(ClubsMemberFactory.values())
                .filter(f -> f.getCheckRole().test(entity.getClubRole()))
                .findAny()
                .map(f -> f.getConvert().apply(entity))
                .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER));
    }
}
