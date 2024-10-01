package hotil.baemo.domains.chat.adapter.output.repository;

import hotil.baemo.domains.chat.domain.chat.ChatRoom;
import hotil.baemo.domains.chat.domain.chat.ChatRoomUser;
import hotil.baemo.domains.chat.domain.value.room.ChatRoomId;
import hotil.baemo.domains.chat.domain.value.club.ClubId;
import hotil.baemo.domains.chat.domain.value.exercise.ExerciseId;
import hotil.baemo.domains.chat.domain.value.user.UserId;

public interface QueryChatRoomUserRepository {
	ChatRoom loadDMChatRoom(ChatRoomId chatRoomId,UserId userId);
	ChatRoom loadExerciseChatRoom(ExerciseId exerciseId);
	ChatRoom loadClubChatRoom(ClubId clubId);
	ChatRoomUser loadChatRoomUser(ChatRoomId chatRoomId, UserId userId);
	boolean isUserUnsubscribed(ChatRoomId chatRoomId, UserId userId);
	boolean isUserExist(ChatRoomId chatRoomId, UserId userId);
}
