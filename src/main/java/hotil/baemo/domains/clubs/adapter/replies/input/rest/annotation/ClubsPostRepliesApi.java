package hotil.baemo.domains.clubs.adapter.replies.input.rest.annotation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag(name = "모임 게시글의 댓글 API")
@RestController
public @interface ClubsPostRepliesApi {
}
