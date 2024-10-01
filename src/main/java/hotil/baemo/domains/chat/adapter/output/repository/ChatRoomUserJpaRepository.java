package hotil.baemo.domains.chat.adapter.output.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomUserEntity;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomUserStatus;

public interface ChatRoomUserJpaRepository extends JpaRepository<ChatRoomUserEntity,Long> {
	List<ChatRoomUserEntity> findByUserId(Long userId);
	List<ChatRoomUserEntity> findByChatRoomId(String chatRoomId);
	Optional<ChatRoomUserEntity> findByChatRoomIdAndUserId(String chatRoomId,Long userId);
	List<ChatRoomUserEntity> findByChatRoomIdAndChatRoomUserStatus(String chatRoomId, ChatRoomUserStatus status);
	void deleteByChatRoomId(String chatRoomId);
	void deleteByChatRoomIdAndUserId(String chatRoomId,Long userId);
	int countByChatRoomId(String chatRoomId);
}