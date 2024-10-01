package hotil.baemo.domains.score.adapter.output.persistence.entity;

import hotil.baemo.domains.score.adapter.output.persistence.entity.converter.ScoreLogConverter;
import hotil.baemo.domains.score.adapter.output.persistence.entity.converter.TeamPointLogConverter;
import hotil.baemo.domains.score.domain.value.score.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_match_score")
@NoArgsConstructor
public class ScoreEntity {
    //ToDo : Created Info & Updated Info 관련 필드 및 로직 작성할 것
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private Long matchId;
    private Long refereeUserId;
    @Convert(converter = ScoreLogConverter.class)
    private List<Team> scoreLog = new ArrayList<>();
    @Convert(converter = TeamPointLogConverter.class)
    private List<Integer> teamAPointLog = new ArrayList<>();
    @Convert(converter = TeamPointLogConverter.class)
    private List<Integer> teamBPointLog = new ArrayList<>();
    private Integer teamAPoint;
    private Integer teamBPoint;

    @Builder
    private ScoreEntity(Long id, Long matchId, Long refereeId, List<Team> scoreLog, List<Integer> teamAPointLog, List<Integer> teamBPointLog, Integer teamAPoint, Integer teamBPoint) {
        this.id = id;
        this.matchId = matchId;
        this.scoreLog = scoreLog;
        this.teamAPointLog = teamAPointLog;
        this.teamBPointLog = teamBPointLog;
        this.teamAPoint = teamAPoint;
        this.teamBPoint = teamBPoint;
    }

    public void update(List<Team> scoreLog, List<Integer> teamAPointLog, List<Integer> teamBPointLog, Integer teamAPoint, Integer teamBPoint) {
        this.scoreLog = scoreLog;
        this.teamAPointLog = teamAPointLog;
        this.teamBPointLog = teamBPointLog;
        this.teamAPoint = teamAPoint;
        this.teamBPoint = teamBPoint;
    }
}
