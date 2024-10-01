package hotil.baemo.domains.chat.domain.value.user;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record ThumbNail(@NotNull String img) {
	public ThumbNail(String img) {
		this.img = img;
		BaemoValueObjectValidator.valid(this);
	}
}

