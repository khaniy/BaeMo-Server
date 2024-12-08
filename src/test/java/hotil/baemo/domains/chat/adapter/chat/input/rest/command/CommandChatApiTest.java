package hotil.baemo.domains.chat.adapter.chat.input.rest.command;

import hotil.baemo.domains.chat.adapter.output.repository.ChatRoomJpaRepository;
import hotil.baemo.support.base.ControllerTestBaseSupport;
import hotil.baemo.support.domain.user.UserSupport;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


class CommandChatApiTest extends ControllerTestBaseSupport {

    @Autowired
    private UserSupport userSupport;
    @Autowired
    private ChatRoomJpaRepository chatRoomJpaRepository;

	@BeforeEach
	void setImportUserServiceSupport() {
		userSupport.setUpUser();
	}

	@Test
	void 채팅방_생성_요청에_성공할_것이다() throws Exception {

		Long targetId = userSupport.appendSampleUser();

		// targetId : null이 아니어야 함
		Assertions.assertThat(targetId).isNotNull();


		final var result = mockMvc.perform(post("/api/chat/{targetId}", targetId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("SUCCESS"))
			.andExpect(jsonPath("$.payload.chatRoomId").exists())
			.andExpect(jsonPath("$.payload.chatRoomId").isString())
			.andExpect(jsonPath("$.payload.isNewChatRoom").isBoolean())
			.andDo(print())
			.andReturn();


		final var body = result.getResponse().getContentAsString();
		final var chatRoomId = objectMapper.readTree(body)
			.path("payload")
			.path("chatRoomId")
			.asText();

		// chatRoomId 형식 검증 (DM)
		Assertions.assertThat(chatRoomId).matches("\\d+-DM-\\d+");

		// isNewChatRoom 검증
		final var isNewChatRoom = objectMapper.readTree(body)
			.path("payload")
			.path("isNewChatRoom")
			.asBoolean();
		Assertions.assertThat(isNewChatRoom).isTrue();

		// 채팅방 존재 여부 확인
		Assertions.assertThat(chatRoomJpaRepository.findByChatRoomId(chatRoomId)).isPresent();
	}
}