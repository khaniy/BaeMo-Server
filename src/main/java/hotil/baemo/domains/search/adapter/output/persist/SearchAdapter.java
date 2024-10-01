package hotil.baemo.domains.search.adapter.output.persist;


import hotil.baemo.domains.search.adapter.output.persist.entity.TotalSearchEntity;
import hotil.baemo.domains.search.adapter.output.persist.repository.SearchExternalQuery;
import hotil.baemo.domains.search.adapter.output.persist.repository.TotalSearchRepository;
import hotil.baemo.domains.search.application.dto.QSearchDTO;
import hotil.baemo.domains.search.application.port.output.SearchOutPort;
import hotil.baemo.domains.search.domain.value.Domain;
import hotil.baemo.domains.search.domain.value.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchAdapter implements SearchOutPort {

    private final TotalSearchRepository totalSearchRepository;
    private final SearchExternalQuery searchExternalQuery;

    @Override
    public QSearchDTO.SearchHome search(Query query) {
        List<TotalSearchEntity> results = totalSearchRepository.searchByKeyword(query.query());
        Map<Domain, List<TotalSearchEntity>> resultsByDomain = results.stream()
            .collect(Collectors.groupingBy(TotalSearchEntity::getDomain));
        List<Long> exerciseIds = resultsByDomain.getOrDefault(Domain.EXERCISE, Collections.emptyList())
            .stream().map(TotalSearchEntity::getId).toList();
        List<Long> clubIds = resultsByDomain.getOrDefault(Domain.CLUB, Collections.emptyList())
            .stream().map(TotalSearchEntity::getId).toList();
        return QSearchDTO.SearchHome.builder()
            .clubs(searchExternalQuery.findClubs(clubIds))
            .exercises(searchExternalQuery.findExercises(exerciseIds))
            .build();
    }
}
