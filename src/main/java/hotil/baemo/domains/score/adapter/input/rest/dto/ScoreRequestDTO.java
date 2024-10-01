package hotil.baemo.domains.score.adapter.input.rest.dto;

import hotil.baemo.domains.score.domain.value.score.Team;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public interface ScoreRequestDTO {

    record UpdateScoreBoard(
        @NotNull
        Action action
    ) implements ScoreRequestDTO {
        public enum Action {
            SCORE_TEAM_A,
            SCORE_TEAM_B,
            REVERT_SCORE
        }
    }


    record UpdateScore(
        @NotNull(message = "점수는 공백일 수 없습니다.")
        @PositiveOrZero(message = "점수는 0 이상이어야 합니다.")
        @Max(value = 31)
        Integer teamAPoint,
        @NotNull(message = "점수는 공백일 수 없습니다.")
        @PositiveOrZero(message = "점수는 0 이상이어야 합니다.")
        @Max(value = 31)
        Integer teamBPoint
    ) implements ScoreRequestDTO {
    }

    record UpdateMatchUser(
        @NotNull(message = "참가자 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        List<MatchUser> matchUsers
    ) implements ScoreRequestDTO {
    }
    record MatchUser(
        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "유저 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Long userId,

        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "팀 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Team team
    ) implements ScoreRequestDTO{
    }


}
