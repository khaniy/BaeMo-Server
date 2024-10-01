package hotil.baemo.core.security.oauth2.service;

import hotil.baemo.core.security.oauth2.persistence.entity.BaeMoOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaeMoOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.error("BaeMoOAuth2UserService.class loadUser() 사용 금지");
        throw new IllegalArgumentException("BaeMoOAuth2UserService.class 사용 금지");
    }

    public BaeMoOAuth2User loadUser(final Long userIdx) {
        log.error("BaeMoOAuth2UserService.class loadUser() 사용 금지");
        throw new IllegalArgumentException("BaeMoOAuth2UserService.class 사용 금지");
    }
}