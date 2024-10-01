package hotil.baemo.domains.chat.domain.value.room;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record TargetId(@NotNull Long id) {
	public TargetId(Long id) {
		this.id = id;
		BaemoValueObjectValidator.valid(this);
	}
}
