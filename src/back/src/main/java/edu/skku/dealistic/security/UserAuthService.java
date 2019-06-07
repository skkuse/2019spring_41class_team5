package edu.skku.dealistic.security;

import edu.skku.dealistic.model.User;
import edu.skku.dealistic.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.getOne(userName);
    }

    public Boolean isUserIdExist(String userId) {
        return userRepository.existsById(userId);
    }

    public void signUp(User user) {
        userRepository.save(user);
    }
}
