package hotil.baemo.domains.clubs.domain.post.specification.query;


import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsId;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DetailViewClubsPostSpecification {
    public static void spec(final ClubsUser clubsUser, ClubsId clubsId) {

    }

    private enum Rule {
        NON_MEMBER(),
        ;
    }
}