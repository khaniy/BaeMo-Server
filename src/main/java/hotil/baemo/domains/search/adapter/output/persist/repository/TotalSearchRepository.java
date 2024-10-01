package hotil.baemo.domains.search.adapter.output.persist.repository;

import hotil.baemo.domains.search.adapter.output.persist.entity.TotalSearchEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TotalSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${spring.profiles.active}")
    private String env;

    public List searchByKeyword(String keyword) {
        String sql = "SELECT DISTINCT id, title, domain " +
            "FROM " + env + ".vw_total_search " +
            "WHERE to_tsvector('public.korean', title) @@ to_tsquery('public.korean', :keyword)";

        Query query = entityManager.createNativeQuery(sql, TotalSearchEntity.class);
        query.setParameter("keyword", keyword);
        return query.getResultList();
    }
}

