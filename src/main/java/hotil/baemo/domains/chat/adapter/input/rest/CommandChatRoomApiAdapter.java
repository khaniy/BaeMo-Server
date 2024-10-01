package hotil.baemo.domains.chat.adapter.input.rest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotil.baemo.core.common.response.ResponseDTO;
import hotil.baemo.core.security.jwt.authentication.BaeMoUserDetails;
import hotil.baemo.domains.chat.adapter.event.dto.ChatRoomDTO;
import hotil.baemo.domains.chat.adapter.input.rest.annotation.ChatApi;
import hotil.baemo.domains.chat.application.usecase.command.CreateChatRoomUseCase;
import hotil.baemo.domains.chat.application.usecase.command.DeleteChatRoomUseCase;
import hotil.baemo.domains.chat.application.usecase.command.UpdateChatRoomUseCase;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.room.TargetId;
import hotil.baemo.domains.chat.domain.value.user.UserId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ChatApi
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class CommandChatRoomApiAdapter {

	private final CreateChatRoomUseCase createChatRoomUseCase;
	private final DeleteChatRoomUseCase deleteChatRoomUseCase;

	@Operation(summary = "채팅방 생성 API")
	@PostMapping("/{targetId}")
	public ResponseDTO<ChatRoomDTO.CreateChatRoomDTO> createDMChatRoom(@AuthenticationPrincipal BaeMoUserDetails user, @PathVariable(name="targetId") Long targetId) {
		return ResponseDTO.ok(createChatRoomUseCase.createChatRoom(
			new UserId(user.userId()),
			new TargetId(targetId)));
	}

	@Operation(summary = "채팅방 삭제 API")
	@DeleteMapping("/{chatRoomId}")
	public void deleteChatRoomId(@AuthenticationPrincipal BaeMoUserDetails user,
		@PathVariable(name="chatRoomId") String chatRoomId) {
		deleteChatRoomUseCase.deleteDMChatRoom(
			new ChatRoomId(chatRoomId),
			new UserId(user.userId()));
	}
}
