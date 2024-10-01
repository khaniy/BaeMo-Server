package hotil.baemo.domains.clubs.domain.post.specification.command;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.clubs.domain.clubs.entity.ClubsUserId;
import hotil.baemo.domains.clubs.domain.post.entity.ClubsPostWriter;

import java.util.Objects;

@FunctionalInterface
public interface AuthorVerification {
    void verifyAndThrow(ClubsUserId clubsUserId, ClubsPostWriter clubsPostWriter);

    static AuthorVerification verifyAndThrow() throws CustomException {
        return (clubsUserId, clubsPostWriter) -> {
            if (!Objects.equals(clubsUserId.id(), clubsPostWriter.id())) {
                throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
            }
        };
    }

    static AuthorVerification exceptionThrow() throws CustomException {
        return (clubsUserId, clubsPostWriter) -> {
            throw new CustomException(ResponseCode.CLUBS_ROLE_RESTRICTED);
        };
    }
}
