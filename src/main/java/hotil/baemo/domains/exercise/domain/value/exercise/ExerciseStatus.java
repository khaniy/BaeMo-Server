package hotil.baemo.domains.exercise.domain.value.exercise;

import lombok.Getter;

@Getter
public enum ExerciseStatus {
    PROGRESS("진행 중"),
    COMPLETE("완료"),
    RECRUITING("모집 중"),
    RECRUITMENT_FINISHED("모집 완료");

    private final String status;

    private ExerciseStatus(String status) {
        this.status = status;
    }
}
