package hotil.baemo.domains.chat.application.dto;

import lombok.Builder;

/**
 * 유저 id
 * 유저 프로필 사진
 * 유저 이름*/
public interface QChatUserProfileDto {
	@Builder
	record UserInfoDto(
		Long userId,
		String userProfileImg,
		String userName
	){
	}
}
