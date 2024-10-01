package hotil.baemo.domains.search.application.port.input;

import hotil.baemo.domains.search.application.dto.QSearchDTO;
import hotil.baemo.domains.search.application.port.output.SearchOutPort;
import hotil.baemo.domains.search.application.usecase.SearchMainPageUseCase;
import hotil.baemo.domains.search.domain.value.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchMainPageInPort implements SearchMainPageUseCase {

    private final SearchOutPort searchOutPort;

    @Override
    public QSearchDTO.SearchHome searchHome(Query query) {
        return searchOutPort.search(query);
    }
}
