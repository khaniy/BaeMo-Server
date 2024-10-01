package hotil.baemo.domains.chat.domain.value.message;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;


public record SendDate(@NotNull String sendDate) {
	public SendDate(String sendDate) {
		this.sendDate = sendDate;
		BaemoValueObjectValidator.valid(this);
	}
}