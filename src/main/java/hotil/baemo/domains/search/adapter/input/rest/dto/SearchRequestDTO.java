package hotil.baemo.domains.search.adapter.input.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public interface SearchRequestDTO {
    record Search(
        @NotBlank(message = "검색어가 공란입니다.")
        @Size(min = 2, max = 10, message = "검색어는 최소 2글자, 최대 10글자 입니다.")
        String query
    ) implements SearchRequestDTO {
    }
}
