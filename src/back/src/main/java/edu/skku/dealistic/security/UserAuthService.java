package edu.skku.dealistic.security;

import edu.skku.dealistic.model.User;
import edu.skku.dealistic.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Security;

/**
 * User Auth Service
 * Main Features:
 * - Provide information that required for authentication.
 *
 * @author Junhyun Kim
 */
@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.getOne(userName);
        return AuthUser.from(user);
    }

    public Boolean isUserIdExist(String userId) {
        return userRepository.existsById(userId);
    }

    public void signUp(User user) {
        userRepository.save(user);
    }

    public User getLoginUser() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getOne(authUser.getId());
    }
}
