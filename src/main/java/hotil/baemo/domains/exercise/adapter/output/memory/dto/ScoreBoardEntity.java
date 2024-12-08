package hotil.baemo.domains.exercise.adapter.output.memory.dto;


import hotil.baemo.domains.exercise.domain.value.match.Team;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreBoardEntity implements Serializable {
    private Long refereeId;
    private String refereeName;
    private Long scoreId;
    private List<Long> subscriberUserIds;
    private List<Integer> teamAPointLog;
    private List<Integer> teamBPointLog;
    private List<Team> scoreLog;
    private Integer teamAPoint;
    private Integer teamBPoint;
}
