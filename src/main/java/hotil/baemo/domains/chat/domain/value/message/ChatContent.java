package hotil.baemo.domains.chat.domain.value.message;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record ChatContent(@NotNull String content) {
	public ChatContent(String content) {
		this.content = content;
		BaemoValueObjectValidator.valid(this);
	}
}