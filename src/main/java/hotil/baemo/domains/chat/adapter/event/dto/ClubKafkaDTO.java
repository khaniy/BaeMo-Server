package hotil.baemo.domains.chat.adapter.event.dto;

import hotil.baemo.core.util.BaeMoObjectUtil;
import lombok.Builder;

public interface ClubKafkaDTO {
	@Builder
	record Create(
		Long clubsId,
		Long userId
	) implements hotil.baemo.domains.clubs.adapter.clubs.output.event.message.ClubsMessage {
		public String asString() {
			return BaeMoObjectUtil.writeValueAsString(this);
		}
	}

	@Builder
	record Join(
		Long clubsId,
		Long userId
	) implements hotil.baemo.domains.clubs.adapter.clubs.output.event.message.ClubsMessage {
		public String asString() {
			return BaeMoObjectUtil.writeValueAsString(this);
		}
	}

	@Builder
	record KickUser(
		Long clubsId,
		Long userId
	) implements hotil.baemo.domains.clubs.adapter.clubs.output.event.message.ClubsMessage {
		public String asString() {
			return BaeMoObjectUtil.writeValueAsString(this);
		}
	}

	@Builder
	record Delete(
		Long clubsId
	) implements hotil.baemo.domains.clubs.adapter.clubs.output.event.message.ClubsMessage {
		public String asString() {
			return BaeMoObjectUtil.writeValueAsString(this);
		}
	}

	@Builder
	record UpdateRole(
		Long clubsId,
		Long userId,
		String clubsRole
	) implements hotil.baemo.domains.clubs.adapter.clubs.output.event.message.ClubsMessage {
		public String asString() {
			return BaeMoObjectUtil.writeValueAsString(this);
		}
	}
}