package hotil.baemo.domains.match.domain.aggregate;


import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.value.match.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchOrders {

    @NotNull
    private final List<MatchOrder> matchOrders;

    public static MatchOrders of(List<MatchOrder> matchOrders) {
        MatchOrders exerciseMatchOrders = new MatchOrders(matchOrders);
        exerciseMatchOrders.validateUniqueOrders();
        return exerciseMatchOrders;
    }

    public Order getNewMatchOrder() {
        if (matchOrders.isEmpty()) {
            return new Order(1);
        }
        MatchOrder lastOrder = getLastMatchOrder();
        return new Order(lastOrder.getOrder().order() + 1);
    }

    public MatchOrders deleteMatchOrder(MatchId matchId) {
        MatchOrder deletedOrder = getMatchOrder(matchId);
        matchOrders.removeIf(m -> m.getMatchId().equals(matchId));
        var updateOrders = reorderMatchOrdersAfterDeletion(deletedOrder.getOrder().order());
        return new MatchOrders(updateOrders);
    }

    public List<MatchId> getMatchIds() {
        return matchOrders.stream().map(MatchOrder::getMatchId).toList();
    }

    public boolean contains(MatchId matchId) {
        return matchOrders.stream()
            .anyMatch(m -> m.getMatchId().equals(matchId));
    }

    public Integer getSize() {
        return matchOrders.size();
    }


    public MatchOrder getMatchOrder(MatchId matchId) {
        return matchOrders.stream()
            .filter(m -> m.getMatchId().equals(matchId))
            .findFirst()
            .orElseThrow(() -> new CustomException(ResponseCode.MATCH_NOT_FOUND));
    }

    public Integer getOrder(MatchId matchId) {
        return getMatchOrder(matchId).getOrder().order();
    }

    private MatchOrder getLastMatchOrder() {
        return matchOrders.stream()
            .max(Comparator.comparingInt(m->m.getOrder().order()))
            .orElse(null); // 반환할 값이 없을 경우 null 반환
    }

    private void validateUniqueOrders() {
        Set<Integer> orders = matchOrders.stream()
            .map(m->m.getOrder().order())
            .collect(Collectors.toSet());
        if (orders.size() != matchOrders.size()) {
            throw new CustomException(ResponseCode.DUPLICATE_ORDER_FOUND);
        }
    }

    private List<MatchOrder> reorderMatchOrdersAfterDeletion(Integer deletedOrder) {
        return matchOrders.stream()
            .map(mo -> {
                if (mo.getOrder().order() > deletedOrder) {
                    return MatchOrder.of(mo.getMatchId(), new Order(mo.getOrder().order() - 1));
                } else {
                    return mo;
                }
            })
            .collect(Collectors.toList());
    }
}

