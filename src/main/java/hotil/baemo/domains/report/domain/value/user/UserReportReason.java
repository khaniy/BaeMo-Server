package hotil.baemo.domains.report.domain.value.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserReportReason {

    INAPPROPRIATE_LANGUAGE("욕설, 비하, 차별 등 부적절한 언어 사용 및 불쾌감을 주는 행동을 하고 있습니다."),
    HARASSMENT("특정 개인이나 그룹을 대상으로 괴롭힘, 협박 등을 일삼고 있습니다."),
    ILLEGAL_ACTIVITY("불법적인 행위를 유도하거나 참여를 강요하거나, 불법적인 정보를 공유하고 있습니다."),
    FRAUD("금전적 이익을 목적으로 사기 행위를 하거나, 타인을 속이고 있습니다."),
    ETC("기타(사유를 반드시 기재해 주세요)");

    private final String description;

}
