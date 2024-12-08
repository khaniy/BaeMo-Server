package hotil.baemo.domains.notification.domains.aggregate;

import com.fasterxml.jackson.annotation.JsonInclude;
import hotil.baemo.core.util.BaeMoObjectUtil;
import hotil.baemo.domains.notification.domains.value.notification.DomainInfo;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NotificationData(
    String id,
    String headerTitle
) {
    public String toString() {
        return BaeMoObjectUtil.writeValueAsString(this);
    }
}
