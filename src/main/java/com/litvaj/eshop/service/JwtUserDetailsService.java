package com.litvaj.eshop.service;


import com.litvaj.eshop.model.AuthenticatedUser;
import com.litvaj.eshop.model.User;
import com.litvaj.eshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for load Authenticated User
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findByEmail(username);

        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            return new AuthenticatedUser(user.getEmail(), user.getPassword());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}