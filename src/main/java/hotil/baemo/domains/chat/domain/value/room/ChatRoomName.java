package hotil.baemo.domains.chat.domain.value.room;

import hotil.baemo.core.validator.BaemoValueObjectValidator;
import jakarta.validation.constraints.NotNull;

public record ChatRoomName(
	@NotNull
	String chatRoomName
) {
	public ChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
		BaemoValueObjectValidator.valid(this);
	}
}
