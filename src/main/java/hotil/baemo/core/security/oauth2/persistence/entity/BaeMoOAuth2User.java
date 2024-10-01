package hotil.baemo.core.security.oauth2.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Builder
@Getter
@Entity
@Table(name = "tb_oauth_remove")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BaeMoOAuth2User implements OAuth2User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String realName;
    private String profileImage;
    private String nickname;

    public Map<String, Object> getAttributes() {
        log.error("BaeMoOAuth2User.class getAttributes() 사용 금지");
        throw new IllegalArgumentException("BaeMoOAuth2User.class 사용 금지");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.error("BaeMoOAuth2User.class getAuthorities() 사용 금지");
        throw new IllegalArgumentException("BaeMoOAuth2User.class 사용 금지");
    }

    @Override
    public String getName() {
        log.error("BaeMoOAuth2User.class getName() 사용 금지");
        throw new IllegalArgumentException("BaeMoOAuth2User.class 사용 금지");
    }
}