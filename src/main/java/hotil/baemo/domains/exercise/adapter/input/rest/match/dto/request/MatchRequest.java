package hotil.baemo.domains.exercise.adapter.input.rest.match.dto.request;

import hotil.baemo.domains.exercise.domain.entity.user.MatchUser;
import hotil.baemo.domains.exercise.domain.entity.user.MatchUsers;
import hotil.baemo.domains.exercise.domain.value.match.MatchStatus;
import hotil.baemo.domains.exercise.domain.value.match.Team;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.stream.Collectors;

public interface MatchRequest {
    record CreateMatchDTO(

        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "운동 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Long exerciseId,

        @NotNull(message = "참가자 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        List<MatchUserDTO> matchUsers
    ) implements MatchRequest {
        public MatchUsers toMatchUsers() {
            return MatchUsers.of(matchUsers.stream().map(matchUser -> MatchUser.builder()
                    .userId(new UserId(matchUser.userId()))
                    .team(matchUser.team())
                    .build()
                ).collect(Collectors.toList())
            );
        }
    }

    record UpdateMatchDTO(
        @NotNull(message = "참가자 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        List<MatchUserDTO> matchUsers

    ) implements MatchRequest {
        public MatchUsers toMatchUsers() {
            return MatchUsers.of(matchUsers.stream().map(matchUser -> MatchUser.builder()
                    .userId(new UserId(matchUser.userId()))
                    .team(matchUser.team())
                    .build()
                ).collect(Collectors.toList())
            );
        }
    }

    record UpdateMatchStatusDTO(
        @NotNull
        MatchStatus matchStatus,

        @Positive(message = "코트 번호는 1에서 100사이로 지정이 가능합니다.")
        @Max(value = 100, message = "코트 번호는 1에서 100사이로 지정이 가능합니다.")
        Integer courtNumber

    ) implements MatchRequest {
    }

    record MatchUserDTO(

        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "유저 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Long userId,

        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "팀 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Team team

    ) implements MatchRequest {
    }
}
