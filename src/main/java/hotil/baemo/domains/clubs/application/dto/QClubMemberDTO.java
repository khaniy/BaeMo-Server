package hotil.baemo.domains.clubs.application.dto;

import hotil.baemo.domains.clubs.domain.value.member.ClubRole;
import lombok.Builder;

import java.util.List;

public interface QClubMemberDTO {

    @Builder
    record Members(
        List<Member> list
    ) implements QClubMemberDTO {
    }

    @Builder
    record Member(
        Long userId,
        Long id, //todo 삭제 예정
        String realName,
        String profilePath, //todo 삭제 예정
        String profileImage,
        ClubRole role,
        String level,
        String gender
    ) implements QClubMemberDTO {
    }
}