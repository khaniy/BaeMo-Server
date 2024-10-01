package hotil.baemo.domains.chat.domain.value.message;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;


public record ChatMessageId(@NotNull Long id) {
	public ChatMessageId(Long id) {
		this.id = id;
		BaemoValueObjectValidator.valid(this);
	}
}