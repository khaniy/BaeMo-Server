package hotil.baemo.domains.chat.adapter.output.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import hotil.baemo.domains.chat.adapter.output.postgres.entity.ChatMessageEntity;
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity,Long> {
	@Modifying(flushAutomatically = true)
	@Query("UPDATE ChatMessageEntity c SET c.unreadCount = c.unreadCount - 1 WHERE c.chatMessageId IN :messageIds AND c.unreadCount > 0")
	int decrementUnreadCountForMessages(@Param("messageIds") List<Long> messageIds);

	@Query("SELECT c.chatMessageId FROM ChatMessageEntity c WHERE c.chatMessageId IN :messageIds AND c.unreadCount > 0")
	List<Long> findMessagesWithPositiveUnreadCount(@Param("messageIds") List<Long> messageIds);

}
