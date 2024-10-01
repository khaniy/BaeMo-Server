package hotil.baemo.domains.chat.adapter.event.dto;

import java.time.ZonedDateTime;
import java.util.List;

import hotil.baemo.domains.exercise.adapter.event.dto.ExerciseEventDTO;
import lombok.Builder;

public interface ExerciseKafkaDTO {
	record Created(
		Long exerciseId,
		String exerciseTitle,
		String exerciseLocation,
		ZonedDateTime exerciseStartTime,
		ZonedDateTime exerciseEndTime,
		String exerciseType,
		Long userId
	) implements ExerciseKafkaDTO {
	}
	record Deleted(
		Long exerciseId,
		String exerciseTitle,
		Long userId
	) implements ExerciseKafkaDTO {
	}
	record UserParticipated(
		Long exerciseId,
		String exerciseTitle,
		String exerciseUserRole,
		String exerciseUserStatus,
		Long userId
	) implements ExerciseKafkaDTO {
	}
	record UserCancelled(
		Long exerciseId,
		String exerciseTitle,
		boolean bySelf,
		Long userId
	) implements ExerciseKafkaDTO {
	}

	record Complete(
		List<Long> exerciseIds

	) implements ExerciseKafkaDTO {
	}

	record UserRoleChanged(
		Long exerciseId,
		String exerciseTitle,
		Long targetUserId,
		String targetExerciseUserRole,
		String targetExerciseUserStatus
	) implements ExerciseKafkaDTO {
	}
}