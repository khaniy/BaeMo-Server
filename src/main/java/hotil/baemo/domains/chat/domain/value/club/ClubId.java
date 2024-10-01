package hotil.baemo.domains.chat.domain.value.club;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClubId(
	@NotNull
	@Positive
	Long clubId
) {
	public ClubId(Long clubId) {
		this.clubId = clubId;
		BaemoValueObjectValidator.valid(this);
	}
}

