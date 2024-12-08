package hotil.baemo.domains.exercise.domain.entity.user;

import hotil.baemo.core.common.response.ResponseCode;
import hotil.baemo.core.common.response.exception.CustomException;
import hotil.baemo.core.validator.BaemoValidator;
import hotil.baemo.domains.exercise.domain.value.match.Team;
import hotil.baemo.domains.exercise.domain.value.user.UserId;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class MatchUsers extends BaemoValidator {

    @NotNull
    @Size(min = 0, max = 4)
    private final List<MatchUser> users;
    private List<MatchUser> deletedUsers;

    private MatchUsers(List<MatchUser> users) {
        this.users = users;
        this.valid();

    }

    public static MatchUsers of(List<MatchUser> matchUsers) {
        return new MatchUsers(matchUsers);
    }

    @Override
    protected void valid() {
        super.valid();
        validDuplicatedId();
        validTeamSize();
    }

    public List<UserId> getUserIds() {
        return users.stream().map(MatchUser::getUserId).collect(Collectors.toList());
    }

    public boolean isTeamDefined() {
        Map<Team, Long> teamCounts = countTeam();
        return teamCounts.getOrDefault(Team.TEAM_A, 0L) == 2 && teamCounts.getOrDefault(Team.TEAM_B, 0L) == 2;
    }

    public MatchUsers update(MatchUsers matchUsers) {
        Map<UserId, MatchUser> existingUsers = users.stream()
            .collect(Collectors.toMap(MatchUser::getUserId, user -> user));
        List<MatchUser> newUsers = matchUsers.getUsers().stream()
            .map(newUser -> updateOrCreateUser(newUser, existingUsers.get(newUser.getUserId())))
            .collect(Collectors.toList());
        return MatchUsers.of(newUsers);
    }


    private void validDuplicatedId() {
        Set<UserId> uniqueIds = users.stream().map(MatchUser::getUserId).collect(Collectors.toSet());
        if (uniqueIds.size() != users.size()) {
            throw new CustomException(ResponseCode.MATCH_USER_ERROR);
        }
    }

    private void validTeamSize() {
        Map<Team, Long> teamCounts = countTeam();
        boolean error = teamCounts.entrySet().stream().anyMatch(entry ->
            (entry.getKey() != Team.UNDEFINED && entry.getValue() > 2));

        if (error) {
            throw new CustomException(ResponseCode.MATCH_USER_ERROR);
        }
    }

    private Map<Team, Long> countTeam() {
        return users.stream().collect(Collectors.groupingBy(MatchUser::getTeam, Collectors.counting()));
    }
    private MatchUser updateOrCreateUser(MatchUser newUser, MatchUser existingUser) {
        return existingUser != null
            ? MatchUser.builder()
            .id(existingUser.getId())
            .userId(newUser.getUserId())
            .team(newUser.getTeam())
            .build()
            : MatchUser.builder()
            .userId(newUser.getUserId())
            .team(newUser.getTeam())
            .build();
    }
}