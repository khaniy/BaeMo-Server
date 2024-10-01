package hotil.baemo.domains.chat.domain.value.message;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record SendUserName(@NotNull String name) {
	public SendUserName(String name) {
		this.name = name;
		BaemoValueObjectValidator.valid(this);
	}
}

