package hotil.baemo.domains.clubs.domain.value.club;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ClubImage(
        @NotNull
        MultipartFile clubProfileImage,
        @NotBlank
        MultipartFile clubBackGroundImage
) {
}
