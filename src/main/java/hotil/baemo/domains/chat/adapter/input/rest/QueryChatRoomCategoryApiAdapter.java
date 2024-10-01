package hotil.baemo.domains.chat.adapter.input.rest;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.chat.adapter.input.rest.annotation.ChatApi;
import hotil.baemo.domains.chat.application.dto.QChatRoomListDto;
import hotil.baemo.domains.chat.application.dto.QChatUserProfileDto;
import hotil.baemo.domains.chat.application.usecase.query.*;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomType;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ChatApi
@RequestMapping("/api/chat")
@RestController
@RequiredArgsConstructor
public class QueryChatRoomCategoryApiAdapter {
	private final RetrieveChatRoomsUseCase retrieveChatRoomsUseCase;
	private final RetrieveChatRoomMessageUseCase retrieveChatRoomMessageUseCase;

	/**
	 * 페이징 처리 API
	 * 2차 MVP 버전 때는 추가 X*/
	// @Operation(summary = "채팅방 조회 API")
	// @GetMapping
	// public ResponseDTO getChatRoomId(@AuthenticationPrincipal BaeMoUserDetails user,
	// 	@RequestParam String chatRoomId,
	// 	@RequestParam(required = false, defaultValue = "20") Integer pageSize,
	// 	@RequestParam(required = false, defaultValue = "1") Integer pageNumber){
	// 	ChatRoomId roomId=new ChatRoomId(chatRoomId);
	// 	return ResponseDTO.ok(retrieveChatRoomMessageUseCase.retrieveChatRoomMessage(
	// 		new UserId(user.userId()),roomId, pageSize, pageNumber));
	// }

	@Operation(summary = "채팅방 조회 API")
	@GetMapping
	public ResponseDTO getChatRoomId(@AuthenticationPrincipal BaeMoUserDetails user,
		@RequestParam String chatRoomId){
		ChatRoomId roomId=new ChatRoomId(chatRoomId);
		return ResponseDTO.ok(retrieveChatRoomMessageUseCase.retrieveChatRoomMessage(
			new UserId(user.userId()),roomId));
	}

	//ㅇ
	@Operation(summary = "채팅 타입 조회")
	@GetMapping("/{type}")
	public ResponseDTO<List<QChatRoomListDto.ChatRoomList>> retrieveAllChatList(
		@AuthenticationPrincipal BaeMoUserDetails user,
		@PathVariable(name = "type") final ChatRoomType type){
		return ResponseDTO.ok(retrieveChatRoomsUseCase.retrieveChatRooms(new UserId(user.userId()),type));
	}

	@Operation(summary = "채팅 유저 프로필 조회")
	@GetMapping("/user/{userId}")
	public ResponseDTO<QChatUserProfileDto.UserInfoDto> retrieveChatUserProfile(
		@AuthenticationPrincipal BaeMoUserDetails user,
		@PathVariable(name = "userId") final Long userId){
		return ResponseDTO.ok(retrieveChatRoomsUseCase.retrieveUserInfo(new UserId(userId)));
	}
}

