package hotil.baemo.domains.report.domain.value.club;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClubReportReason {

    INAPPROPRIATE_OR_ILLEGAL_BEHAVIOR("선정적이거나 불법적인 행위를 하고 있습니다."),
    VIOLENCE_OR_HARASSMENT("폭력 및 괴롭힘과 같은 행위를 하고 있습니다."),
    FRAUD("사기 행위를 저지르고 있습니다."),
    OTHER("기타(사유를 반드시 기재해 주세요)");


    private final String description;

}
