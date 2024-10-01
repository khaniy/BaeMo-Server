package hotil.baemo.domains.notice.application.port.input;

import hotil.baemo.core.aws.AwsS3Service;
import hotil.baemo.core.aws.value.DomainType;
import hotil.baemo.core.aws.value.PreSignedUrl;
import hotil.baemo.domains.notice.application.port.output.CommandNoticeOutPort;
import hotil.baemo.domains.notice.application.usecase.CreateNoticeUseCase;
import hotil.baemo.domains.notice.domain.aggregate.Notice;
import hotil.baemo.domains.notice.domain.aggregate.NoticeImages;
import hotil.baemo.domains.notice.domain.spec.NoticeSpecification;
import hotil.baemo.domains.notice.domain.spec.NoticeUserSpecification;
import hotil.baemo.domains.notice.domain.value.notice.NoticeContent;
import hotil.baemo.domains.notice.domain.value.notice.NoticeTitle;
import hotil.baemo.domains.notice.domain.value.user.NoticeUserRole;
import hotil.baemo.domains.notice.domain.value.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CreateNoticeInPort implements CreateNoticeUseCase {

    private final NoticeUserSpecification noticeUserSpecification;
    private final CommandNoticeOutPort commandNoticeOutPort;
    private final AwsS3Service awsS3Service;

    @Override
    @Transactional
    public void createNotice(
        UserId userId,
        NoticeTitle title,
        NoticeContent content,
        NoticeImages images
    ) {
        NoticeUserRole role = noticeUserSpecification.getRole(userId);
        Notice notice = NoticeSpecification.spec(role)
            .createNotice(userId, title, content, images);
        commandNoticeOutPort.save(notice);
    }

    @Override
    public List<PreSignedUrl.Put> createPreSignedURL(UserId userId, Integer count) {
        NoticeUserRole role = noticeUserSpecification.getRole(userId);
        NoticeSpecification.spec(role);
        return IntStream.range(0, count).mapToObj(i -> CompletableFuture.supplyAsync(() ->
                awsS3Service.createPutSignatureUrl(DomainType.NOTICE, userId.id()))
            )
            .map(CompletableFuture::join)
            .collect(Collectors.toList());
    }
}
