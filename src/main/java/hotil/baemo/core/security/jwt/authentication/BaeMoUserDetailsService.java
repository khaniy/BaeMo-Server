package hotil.baemo.core.security.jwt.authentication;

import hotil.baemo.domains.users.adapter.output.persistence.repository.AbstractBaeMoUsersEntityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BaeMoUserDetailsService implements UserDetailsService {
    private final AbstractBaeMoUsersEntityJpaRepository abstractBaeMoUsersEntityJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return abstractBaeMoUsersEntityJpaRepository.loadByPhone(username);
    }

    public UserDetails loadUserByUserId(Long userId) throws UsernameNotFoundException {
        return abstractBaeMoUsersEntityJpaRepository.loadById(userId);
    }
}