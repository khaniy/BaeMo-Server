package hotil.baemo.domains.match.adapter.input.rest.dto.request;

import hotil.baemo.domains.match.domain.value.match.Team;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public interface MatchRequest {
    record CreateMatchDTO(

        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "운동 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Long exerciseId,

        @NotNull(message = "참가자 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        List<MatchUserDTO> matchUsers,

        @NotNull(message = "코트 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        @Positive(message = "코트 번호는 1에서 100사이로 지정이 가능합니다.")
        @Max(value = 100, message = "코트 번호는 1에서 100사이로 지정이 가능합니다.")
        Integer courtNumber

    ) implements MatchRequest {
    }

    record UpdateMatchDTO(
        @NotNull(message = "참가자 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        List<MatchUserDTO> matchUsers,

        @NotNull(message = "코트 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        @Positive(message = "코트 번호는 1에서 100사이로 지정이 가능합니다.")
        @Max(value = 100, message = "코트 번호는 1에서 100사이로 지정이 가능합니다.")
        Integer courtNumber

    ) implements MatchRequest {
    }

    record UpdateMatchOrderDTO(

        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "게임 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Long exerciseId,

        @NotNull(message = "게임 순서는 공백일 수 없습니다. 다시 시도해주세요.")
        List<MatchOrderDTO> matchOrders

    ) implements MatchRequest {
    }

    record UpdateMatchStatusDTO(
        @NotNull
        Action action

    ) implements MatchRequest {
        public enum Action {
            PREVIOUS,
            NEXT
        }
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

    record MatchOrderDTO(

        @Positive(message = "잘못된 요청입니다. 다시 시도해주세요.")
        @NotNull(message = "게임 정보는 공백일 수 없습니다. 다시 시도해주세요.")
        Long matchId,

        @NotNull(message = "게임 순서는 공백일 수 없습니다. 다시 시도해주세요.")
        @Positive(message = "게임 순서는 1부터 지정가능 합니다.")
        Integer matchOrder

    ) implements MatchRequest {
    }


}
