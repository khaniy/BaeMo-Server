package hotil.baemo.domains.community.domain.specification;

import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.Writer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteSpecification {

    public static Predicate<Community> test(final Writer writer) {
        // TODO : 관리자 권한 추가하기
        return community -> community.getWriter().equals(writer);
    }
}
