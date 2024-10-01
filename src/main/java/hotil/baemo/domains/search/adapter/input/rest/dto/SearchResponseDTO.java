package hotil.baemo.domains.search.adapter.input.rest.dto;

public interface SearchResponseDTO {
    record Search(
        String result
    ) implements SearchResponseDTO {
    }
}
