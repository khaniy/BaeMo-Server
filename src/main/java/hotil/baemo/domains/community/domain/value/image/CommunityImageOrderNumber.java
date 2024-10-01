package hotil.baemo.domains.community.domain.value.image;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CommunityImageOrderNumber(
    @NotNull
    @Positive
    Long orderNumber
) {

    public CommunityImageOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }
}