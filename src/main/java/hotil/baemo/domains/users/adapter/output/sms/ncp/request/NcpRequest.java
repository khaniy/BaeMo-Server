package hotil.baemo.domains.users.adapter.output.sms.ncp.request;

import lombok.Builder;

import java.util.List;

public interface NcpRequest {
    @Builder
    record JoinDTO(
        String type,
        String from,
        String content,
        List<Messages> messages
    ) implements NcpRequest {
    }

    @Builder
    record Messages(
        String to
    ) implements NcpRequest {
    }
}
