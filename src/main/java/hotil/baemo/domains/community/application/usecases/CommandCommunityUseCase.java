package hotil.baemo.domains.community.application.usecases;

import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.Writer;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.domains.community.domain.value.CommunityContent;
import hotil.baemo.domains.community.domain.value.CommunityTitle;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommandCommunityUseCase {
    Community create(Writer writer, CommunityCategory category, CommunityTitle title, CommunityContent content, List<MultipartFile> imageList);

    void update(CommunityId communityId, Writer writer, CommunityCategory category, CommunityTitle title, CommunityContent content,List<MultipartFile> imageList);

    void delete(CommunityId communityId, Writer writer);
}