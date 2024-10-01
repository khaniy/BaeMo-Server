package hotil.baemo.domains.community.application.ports.output;

import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.CommunityUserId;
import hotil.baemo.domains.community.domain.entity.Writer;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommandCommunityOutputPort {
    CommunityId save(Community community, List<MultipartFile> imageList);

    void update(Community community, List<MultipartFile> imageList);

    void remove(CommunityId community, Writer writer);

    void subscript(List<CommunityCategory> list, CommunityUserId userId);
}