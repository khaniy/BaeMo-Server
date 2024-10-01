package hotil.baemo.core.aws.value;

import lombok.Builder;

import static hotil.baemo.core.aws.value.PreSignedUrl.Get;
import static hotil.baemo.core.aws.value.PreSignedUrl.Put;

public sealed interface PreSignedUrl permits Put, Get {
    @Builder
    record Put(
        String preSignedUrl,
        String savedUrl
    ) implements PreSignedUrl {
    }

    @Builder
    record Get(
        String url
    ) implements PreSignedUrl {
    }
}
