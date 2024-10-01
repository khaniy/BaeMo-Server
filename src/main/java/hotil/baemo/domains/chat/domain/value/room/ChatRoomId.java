package hotil.baemo.domains.chat.domain.value.room;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;
public record ChatRoomId(
	@NotNull
	String id
) {
	public ChatRoomId(String id) {
		this.id = id;
		BaemoValueObjectValidator.valid(this);
	}
}
