package hotil.baemo.domains.relation.adapter.input.rest.dto.request;

public interface RelationRequest {
    record FriendDTO(
        String userCode
    ) implements RelationRequest {
    }
    record AddFriend(
        String userCode,
        String userName
    ) implements RelationRequest {
    }
}