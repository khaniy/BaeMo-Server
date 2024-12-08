package hotil.baemo.support.domain.user;

import hotil.baemo.domains.users.adapter.output.persistence.entity.BaeMoUserEntity;
import hotil.baemo.domains.users.adapter.output.persistence.entity.DeviceEntity;
import hotil.baemo.domains.users.adapter.output.persistence.repository.BaeMoUserJpaRepository;
import hotil.baemo.domains.users.adapter.output.persistence.repository.DeviceJpaRepository;
import hotil.baemo.support.base.FixtureMonkeyBaseSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserSupport extends FixtureMonkeyBaseSupport {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BaeMoUserJpaRepository baeMoUserJpaRepository;
    @Autowired
    private DeviceJpaRepository deviceJpaRepository;

    private Long userId;
    private String password;
    private String name;
    private String profile;
    private String phone;

    public BaeMoUserEntity createBaeMoUser() {
        return setBaeMoUser();
    }

    public BaeMoUserEntity setUpUser() {
        final var baeMoUser = setBaeMoUser();
        setAuthentication(baeMoUser);
        return baeMoUser;
    }

    public Long appendSampleUser() {
        BaeMoUserEntity sample = monkey.giveMeBuilder(BaeMoUserEntity.class)
            .setNull("id")
            .set("profileImage", "profileImage")
            .set("isDel", false)
            .sample();
        BaeMoUserEntity saved = baeMoUserJpaRepository.save(sample);
        deviceJpaRepository.save(
            monkey.giveMeBuilder(DeviceEntity.class)
                .set("userId", saved.getId())
                .set("isDel", false)
                .sample()
        );
        return saved.getId();
    }

    public List<Long> appendSampleUser(int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> appendSampleUser())
            .collect(Collectors.toList());
    }

    private void setAuthentication(final BaeMoUserEntity baeMoUser) {
        final var authentication = new UsernamePasswordAuthenticationToken(baeMoUser, baeMoUser.getAuthorities(), baeMoUser.getAuthorities());
        final var context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
    }

    private BaeMoUserEntity setBaeMoUser() {
        final var baeMoUser = monkey.giveMeBuilder(BaeMoUserEntity.class)
            .setNull("id")
            .set("profileImage", "profileUrl")
            .set("isDel", false)
            .sample();

        final var password = baeMoUser.getPassword();
        final var encode = passwordEncoder.encode(password);

        baeMoUser.updatePassword(encode);

        final var saved = baeMoUserJpaRepository.save(baeMoUser);
        deviceJpaRepository.save(
            monkey.giveMeBuilder(DeviceEntity.class)
                .set("userId", saved.getId())
                .set("isDel", false)
                .sample()
        );

        this.userId = saved.getId();
        this.password = password;
        this.profile = baeMoUser.profile();
        this.name = baeMoUser.name();
        this.phone = baeMoUser.getPhone();

        return baeMoUser;
    }

    public Long getUserId() {
        return userId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPhone() {
        return this.phone;
    }
}