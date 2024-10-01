package hotil.baemo.core.util;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public final class BaeMoQueryUtil {

    public <T> OrderSpecifier<?>[] createOrderSpecifier(
        final Pageable pageable,
        final Function<String, Expression<? extends Comparable<?>>> path
    ) {

        final List<OrderSpecifier<?>> list = new ArrayList<>();

        pageable.getSort()
            .forEach(e -> {
                final var order = e.isAscending() ? Order.ASC : Order.DESC;
                final var property = e.getProperty();
                final var orderSpecifier = new OrderSpecifier<>(order, path.apply(property));

                list.add(orderSpecifier);
            });

        return list.toArray(new OrderSpecifier[0]);
    }
}