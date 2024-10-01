package hotil.baemo.domains.search.application.port.output;

import hotil.baemo.domains.search.application.dto.QSearchDTO;
import hotil.baemo.domains.search.domain.value.Query;

public interface SearchOutPort {

    QSearchDTO.SearchHome search(Query query);

}
