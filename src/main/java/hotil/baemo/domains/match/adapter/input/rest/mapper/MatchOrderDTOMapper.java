package hotil.baemo.domains.match.adapter.input.rest.mapper;

import hotil.baemo.domains.match.adapter.input.rest.dto.request.MatchRequest;
import hotil.baemo.domains.match.domain.aggregate.MatchOrder;
import hotil.baemo.domains.match.domain.value.match.MatchId;
import hotil.baemo.domains.match.domain.aggregate.MatchOrders;
import hotil.baemo.domains.match.domain.value.match.Order;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MatchOrderDTOMapper {
    public static MatchOrders toMatchOrders(MatchRequest.UpdateMatchOrderDTO dto) {
        List<MatchOrder> matchOrders = dto.matchOrders().stream()
            .map(d -> MatchOrder.of(
                new MatchId(d.matchId()),
                new Order(d.matchOrder())))
            .toList();
        return MatchOrders.of(matchOrders);
    }
}
