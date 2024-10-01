package hotil.baemo.domains.match.adapter.output.persistence.entity;

import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import hotil.baemo.domains.match.domain.value.match.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_match_user")
@NoArgsConstructor
public class MatchUserEntity {
    //ToDo : Created Info & Updated Info 관련 필드 및 로직 작성할 것
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long exerciseId;
    private Long matchId;
    private Long userId;
    @Enumerated(value = EnumType.STRING)
    private Team team;
    @Enumerated(value = EnumType.STRING)
    private MatchStatus matchStatus;

    @Builder
    public MatchUserEntity(Long id, Long exerciseId, Long matchId, Long userId, Team team, MatchStatus matchStatus) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.matchId = matchId;
        this.userId = userId;
        this.team = team;
        this.matchStatus = matchStatus;
    }

    public void update(Long userId, Team team, Long matchId) {
        this.matchId = matchId;
        this.userId = userId;
        this.team = team;
    }

    public void updateTeam(Team team) {
        this.team = team;
    }
}
