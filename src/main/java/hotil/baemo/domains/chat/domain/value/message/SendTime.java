package hotil.baemo.domains.chat.domain.value.message;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;


public record SendTime(@NotNull String time) {
	public SendTime(String time) {
		this.time = time;
		BaemoValueObjectValidator.valid(this);
	}
}