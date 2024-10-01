package hotil.baemo.core.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaeMoOAuth2UserNameProvider {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final List<String> PREFIX = List.of(
        "강력한 ", "받기 어려운 ", "팀을 위한 ", "심판을 속이는 ",
        "부드러운 ", "점수를 내는 ", "몸을 날려 ", "빠른 ", "훌륭한 ",
        "경이로운 ", "재치있는 ", "듀스중에 "
    );

    private static final List<String> SUFFIX = List.of(
        "스매시", "서브", "셔틀콕", "클리어", "드롭", "드라이브", "푸시",
        "헤어핀", "커트", "스트로크", "백원드 점프 스매시", "백핸드 스트로크", "랠리", "네트 플레이"
    );

    public static String getNickname() {
        return PREFIX.get(getRandomIndex(PREFIX.size()))
            + SUFFIX.get(getRandomIndex(SUFFIX.size()));
    }

    private static int getRandomIndex(int max) {
        return SECURE_RANDOM.nextInt(max);
    }
}
