package hotil.baemo.domains.chat.domain.value.message;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record SenderId(@NotNull Long senderId) {
	public SenderId(Long senderId) {
		this.senderId = senderId;
		BaemoValueObjectValidator.valid(this);
	}
}