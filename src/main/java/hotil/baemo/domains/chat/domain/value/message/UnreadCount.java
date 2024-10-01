package hotil.baemo.domains.chat.domain.value.message;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record UnreadCount(@NotNull Integer unreadCount) {
	public UnreadCount(Integer unreadCount) {
		this.unreadCount = unreadCount;
		BaemoValueObjectValidator.valid(this);
	}
}