package hotil.baemo.domains.chat.adapter.output.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatRoomEntity;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity,Long> {
	Optional<ChatRoomEntity> findByChatRoomId(String chatRoodId);
	Optional<ChatRoomEntity> findByChatRoomIdContaining(String pattern);
	void deleteByChatRoomId(String chatRoomId);
}
