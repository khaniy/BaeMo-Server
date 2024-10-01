package hotil.baemo.domains.clubs.adapter.clubs.input.external.excersice.impl;


import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.adapter.clubs.output.persistence.repository.ClubsMemberJpaRepository;
import hotil.baemo.domains.clubs.adapter.clubs.input.external.excersice.RetrieveClubRoleAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RetrieveClubRoleAdapterImpl implements RetrieveClubRoleAdapter {
    private final ClubsMemberJpaRepository clubsMemberJpaRepository;

    @Override
    public String getRole(Long clubsId, Long usersId) {
        return clubsMemberJpaRepository.findByUsersIdAndClubsId(usersId, clubsId)
                .orElseThrow(() -> new CustomException(ResponseCode.CLUBS_NOT_FOUND_MEMBER))
                .getClubRole().name();
    }
}
