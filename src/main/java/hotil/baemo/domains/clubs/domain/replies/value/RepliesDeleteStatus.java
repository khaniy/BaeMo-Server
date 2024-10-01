package hotil.baemo.domains.clubs.domain.replies.value;

import jakarta.annotation.Nonnull;

public record RepliesDeleteStatus(
        @Nonnull
        Boolean isDelete
) {
    public RepliesDeleteStatus(
            @Nonnull
            Boolean isDelete
    ) {
        this.isDelete = isDelete;
    }

    public static RepliesDeleteStatus init() {
        return new RepliesDeleteStatus(Boolean.FALSE);
    }

    public static RepliesDeleteStatus delete() {
        return new RepliesDeleteStatus(Boolean.TRUE);
    }

    public static RepliesDeleteStatus restore() {
        return new RepliesDeleteStatus(Boolean.FALSE);
    }
}