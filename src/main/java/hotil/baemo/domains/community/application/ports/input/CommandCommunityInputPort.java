package hotil.baemo.domains.community.application.ports.input;

import hotil.baemo.domains.community.application.ports.output.CommandCommunityOutputPort;
import hotil.baemo.domains.community.application.ports.output.QueryCommunityOutputPort;
import hotil.baemo.domains.community.application.usecases.CommandCommunityUseCase;
import hotil.baemo.domains.community.domain.entity.Community;
import hotil.baemo.domains.community.domain.entity.CommunityId;
import hotil.baemo.domains.community.domain.entity.Writer;
import hotil.baemo.domains.community.domain.value.CommunityCategory;
import hotil.baemo.domains.community.domain.value.CommunityContent;
import hotil.baemo.domains.community.domain.value.CommunityTitle;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandCommunityInputPort implements CommandCommunityUseCase {
    private final CommandCommunityOutputPort commandCommunityOutputPort;
    private final QueryCommunityOutputPort queryCommunityOutputPort;

    @Override
    public Community create(Writer writer, CommunityCategory category, CommunityTitle title, CommunityContent content, List<MultipartFile> imageList) {
        final Community community = Community.builder()
            .writer(writer)
            .communityCategory(category)
            .communityTitle(title)
            .communityContent(content)
            .build();

        final var communityId = commandCommunityOutputPort.save(community, imageList);

        return queryCommunityOutputPort.load(communityId);
    }

    @Override
    public void update(CommunityId communityId, Writer writer, CommunityCategory category, CommunityTitle title, CommunityContent content, List<MultipartFile> imageList) {
        final var community = queryCommunityOutputPort.load(communityId);
        community.updateCategory(category);
        community.updateContent(content);
        community.updateTitle(title);
        commandCommunityOutputPort.update(community, imageList);
    }

    @Override
    public void delete(CommunityId communityId, Writer writer) {
        commandCommunityOutputPort.remove(communityId, writer);
    }
}