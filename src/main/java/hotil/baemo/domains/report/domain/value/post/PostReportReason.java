package hotil.baemo.domains.report.domain.value.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostReportReason {

    SPAM_OR_REPETITIVE("스팸 및 도배성 게시글 입니다."),
    EXPLICIT_CONTENT("선정적인 내용이 포함되어있습니다."),
    ABUSIVE_OR_VIOLENT("욕설 및 폭력적인 내용이 포함되어있습니다."),
    ILLEGAL_ACTIVITY("불법적인 활동에 대한 내용이 포함되어있습니다."),
    ETC("기타(사유를 반드시 기재해 주세요)");

    private final String description;

}
