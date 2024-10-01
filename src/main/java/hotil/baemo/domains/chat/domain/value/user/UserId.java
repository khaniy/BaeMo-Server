package hotil.baemo.domains.chat.domain.value.user;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UserId(
	@Positive
	@NotNull
	Long id
) {
	public UserId(Long id) {
		this.id = id;
		BaemoValueObjectValidator.valid(this);
	}
}
