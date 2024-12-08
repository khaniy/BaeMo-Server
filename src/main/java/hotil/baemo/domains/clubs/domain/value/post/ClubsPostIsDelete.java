package hotil.baemo.domains.clubs.domain.value.post;

import jakarta.validation.constraints.NotNull;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public record ClubsPostIsDelete(
        @NotNull
        Boolean isDelete
) {

    public static ClubsPostIsDelete init() {
        return new ClubsPostIsDelete(FALSE);
    }

    public static ClubsPostIsDelete delete() {
        return new ClubsPostIsDelete(TRUE);
    }
}
