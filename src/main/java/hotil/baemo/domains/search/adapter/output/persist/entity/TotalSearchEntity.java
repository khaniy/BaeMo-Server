package hotil.baemo.domains.search.adapter.output.persist.entity;


import hotil.baemo.domains.search.domain.value.Domain;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "vw_total_search")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalSearchEntity {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Domain domain;
    private String title;

    @Builder
    private TotalSearchEntity(Long id, Domain domain, String title) {
        this.id = id;
        this.domain = domain;
        this.title = title;
    }
}