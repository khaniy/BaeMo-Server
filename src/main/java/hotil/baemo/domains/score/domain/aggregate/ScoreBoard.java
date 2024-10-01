package hotil.baemo.domains.score.domain.aggregate;

import hotil.baemo.domains.score.domain.value.match.MatchId;
import hotil.baemo.domains.score.domain.value.user.UserId;
import hotil.baemo.domains.score.domain.value.user.UserName;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ScoreBoard {
    private final Score score;
    private final UserId refereeId;
    private final UserName refereeName;
    private final List<UserId> subscribers;

    @Builder
    private ScoreBoard(Score score, UserId refereeId, UserName refereeName, List<UserId> subscribers) {
        this.score = score;
        this.refereeId = refereeId;
        this.refereeName = refereeName;
        this.subscribers = subscribers;
    }

    public MatchId getMatchId() {
        return this.score.getMatchId();
    }

    public void scoreTeamA() {
        this.score.scoreTeamA();
    }

    public void scoreTeamB() {
        this.score.scoreTeamB();
    }

    public void revertScore() {
        this.score.revertScore();
    }

    public static ScoreBoard initScoreBoard(UserId refereeId,UserName refereeName, Score score) {
        return ScoreBoard.builder()
            .refereeId(refereeId)
            .refereeName(refereeName)
            .subscribers(new ArrayList<>())
            .score(score)
            .build();
    }

    public boolean isSubscribed(UserId userId) {
        return this.subscribers.contains(userId);
    }

    public boolean isReferee(UserId userId) {
        return this.refereeId.equals(userId);
    }

    public void subscribe(UserId subscriberId) {
        this.subscribers.add(subscriberId);
    }

    public void unsubscribe(UserId userId) {
        if (isSubscribed(userId)) {
            this.subscribers.remove(userId);
        }
    }
}
