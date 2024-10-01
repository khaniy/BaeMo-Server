package hotil.baemo.domains.match.adapter.output.persistence.entity;

import hotil.baemo.core.common.persistence.BaeMoBaseEntity;
import hotil.baemo.domains.match.adapter.output.persistence.entity.converter.TeamLogConverter;
import hotil.baemo.domains.match.adapter.output.persistence.entity.converter.UserLogConverter;
import hotil.baemo.domains.match.domain.value.match.MatchStatus;
import hotil.baemo.domains.match.domain.value.match.Team;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "tb_match")
@SQLDelete(sql = "UPDATE tb_match SET is_del = true WHERE id = ?")
@NoArgsConstructor
public class MatchEntity extends BaeMoBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long exerciseId;
    private Integer courtNumber;
    private Integer matchOrder;
    @Enumerated(value = EnumType.STRING)
    private MatchStatus matchStatus;
    private boolean definedTeam;

    @Column(name = "is_del")
    private boolean isDel;
    @Convert(converter = UserLogConverter.class)
    private List<Long> userLog = new ArrayList<>();
    @Convert(converter = TeamLogConverter.class)
    private List<Team> teamLog = new ArrayList<>();


    @Builder
    private MatchEntity(Long id, Long exerciseId, Integer courtNumber, Integer matchOrder, MatchStatus matchStatus, boolean definedTeam, List<Long> userLog, List<Team> teamLog) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.courtNumber = courtNumber;
        this.matchOrder = matchOrder;
        this.matchStatus = matchStatus;
        this.definedTeam = definedTeam;
        this.userLog = userLog;
        this.teamLog = teamLog;
    }

    public void updateMatch(Integer courtNumber, Integer matchOrder, boolean isTeamDefined) {
        this.courtNumber = courtNumber;
        this.matchOrder = matchOrder;
        this.definedTeam = isTeamDefined;
    }

    public void updateMatchOrder(Integer matchOrder) {
        this.matchOrder = matchOrder;
    }

    public void updateMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public void completeMatch() {
        this.matchStatus = MatchStatus.COMPLETE;
    }

    public void scoringMatch() {
        this.matchStatus = MatchStatus.PROGRESS_SCORING;
    }

    public void historyMatch() {
        this.matchStatus = MatchStatus.HISTORY;
    }

    public void undefinedTeam() {
        this.definedTeam = false;
    }

    public boolean isProgressiveOrCompleteMatch() {
        return matchStatus == MatchStatus.PROGRESS || matchStatus == MatchStatus.COMPLETE;
    }


    public boolean isCompleteMatch() {
        return matchStatus == MatchStatus.COMPLETE || matchStatus == MatchStatus.HISTORY;
    }

}
