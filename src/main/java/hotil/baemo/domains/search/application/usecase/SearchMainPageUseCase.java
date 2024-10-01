package hotil.baemo.domains.search.application.usecase;

import hotil.baemo.domains.search.application.dto.QSearchDTO;
import hotil.baemo.domains.search.domain.value.Query;

public interface SearchMainPageUseCase {

    QSearchDTO.SearchHome searchHome(Query dto);
}
