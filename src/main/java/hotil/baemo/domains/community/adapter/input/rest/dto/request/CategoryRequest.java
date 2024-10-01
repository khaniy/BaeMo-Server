package hotil.baemo.domains.community.adapter.input.rest.dto.request;

import hotil.baemo.domains.community.domain.value.CategoryList;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import lombok.Builder;

import java.util.List;

public interface CategoryRequest {
    @Builder
    record SubscribeDTO(
            List<String> categoryList
    ) implements CategoryRequest {
        public CategoryList toCategoryList(){
            return CategoryList.getInstanceFromDescription(categoryList);
        }
    }
}
