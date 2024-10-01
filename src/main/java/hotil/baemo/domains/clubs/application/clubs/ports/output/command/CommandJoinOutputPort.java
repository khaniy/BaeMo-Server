package hotil.baemo.domains.clubs.application.clubs.ports.output.command;

import hotil.baemo.domains.clubs.domain.clubs.value.Join;

public interface CommandJoinOutputPort {
    void saveJoinRequest(Join.Request joinRequest);

    void saveJoinResult(Join.Result joinResult);
}
