package hotil.baemo.domains.users.adapter.output.sms.ncp.response;

import lombok.Builder;

import java.util.List;

public interface NcpResponse {
    @Builder
    record DTO (
        String statusCode,
        String statusName,
        List<MessageDTO> messages
    )implements NcpResponse{}

    record MessageDTO(
        String messageId,
        String requestTime,
        String from,
        String to,
        String contentType,
        String countryCode,
        String content,
        String completeTime,
        String status,
        String telcoCode,
        String statusCode,
        String statusMessage,
        String statusName
    ) implements NcpResponse{
    }
}
