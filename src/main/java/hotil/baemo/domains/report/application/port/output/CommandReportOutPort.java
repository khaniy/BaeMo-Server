package hotil.baemo.domains.report.application.port.output;

import hotil.baemo.domains.report.domain.aggregate.PostReport;

public interface CommandReportOutPort {
    void save(PostReport postReport);
}
