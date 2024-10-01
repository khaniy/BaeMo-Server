package hotil.baemo.domains.community.domain.specification;

import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.Writer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSpecification {

    public static Predicate<Community> test(final Writer writer) {
        return community -> community.getWriter().equals(writer);
    }
}
