package hotil.baemo.domains.report.adapter.output.persist;

import hotil.baemo.domains.report.application.port.output.CommandReportOutPort;
import hotil.baemo.domains.report.domain.aggregate.PostReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommandReportPersistAdapter implements CommandReportOutPort {

//    private final ReportJpaRepository reportJpaRepository;

    @Override
    public void save(PostReport postReport) {

//        reportJpaRepository.save(ReportEntityMapper.toEntity(postReport));
    }
}
