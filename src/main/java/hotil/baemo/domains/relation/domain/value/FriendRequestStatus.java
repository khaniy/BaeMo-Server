package hotil.baemo.domains.relation.domain.value;

public enum FriendRequestStatus {
	RECEIVER_PENDING,  // 요청을 받은 상태 (승인/거절 버튼 표시)
	SENDER_PENDING,    // 요청을 보낸 상태 (대기 중 표시)
	CONFIRMED,         // 친구 요청이 승인된 상태 (친구 추가 버튼 비활성화)
	REJECTED,           // 친구 요청이 거절된 상태 (친구 버튼 활성화)
	NOT_REQUESTED     // 친구 요청이 없는 상태
}

