package hotil.baemo.domains.clubs.adapter.clubs.input.rest.mapper;

import hotil.baemo.domains.clubs.adapter.clubs.input.rest.dto.request.CommandClubsRequest;
import hotil.baemo.domains.clubs.domain.clubs.entity.Clubs;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsDescription;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsLocation;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsName;
import hotil.baemo.domains.clubs.domain.clubs.value.ClubsSimpleDescription;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandClubsMapper {
    public static Clubs convert(CommandClubsRequest.UpdateClubsDTO request) {
        return Clubs.builder()
                .clubsName(new ClubsName(request.clubsName()))
                .clubsSimpleDescription(new ClubsSimpleDescription(request.clubsSimpleDescription()))
                .clubsDescription(new ClubsDescription(request.clubsDescription()))
                .clubsLocation(new ClubsLocation(request.clubsLocation()))
                .build();
    }
}
