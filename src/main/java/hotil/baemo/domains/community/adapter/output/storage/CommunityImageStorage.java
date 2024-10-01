package hotil.baemo.domains.community.adapter.output.storage;

import hotil.baemo.core.nhn.service.NHNObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityImageStorage {
    private final NHNObjectStorageService nhnObjectStorageService;

    public List<String> saveImage(final List<MultipartFile> imageList) {
        return imageList.stream()
            .map(nhnObjectStorageService::write)
            .toList();
    }
}
