package hotil.baemo.domains.users.adapter.input.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public interface FindRequest {
    @Builder
    record UpdatePasswordDTO(
        @NotBlank(message = "핸드폰 번호가 잘못되었습니다.")
        @Pattern(regexp = "[0-9]{11}", message = "핸드폰 번호가 잘못되었습니다.")
        String phone,
        @NotBlank(message = "비밀번호가 잘못되었습니다.")
        String password
    ) implements FindRequest {
    }
}