package hotil.baemo.domains.users.adapter.output.sms.ncp.response;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NcpResponseDecoder {

    public static void checkResponse(NcpResponse.DTO response) {
        response.messages().forEach(NcpResponseDecoder::checkMessages);
    }

    private static void checkMessages(NcpResponse.MessageDTO message) {
        if (isMessageFailed(message)) {
            if(isStatusCodeFailed(message)) {
                throw new CustomException(ResponseCode.UNAVAILABLE_PHONE_NUMBER);
            }
            throw new CustomException(ResponseCode.SMS_SERVER_ERROR);
        }
    }

    private static boolean isMessageFailed(NcpResponse.MessageDTO message){
        return message.statusName() == "fail";
    }

    private static boolean isStatusCodeFailed(NcpResponse.MessageDTO message){
        return message.statusCode().startsWith("3");
    }


}
