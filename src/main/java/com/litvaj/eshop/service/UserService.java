package com.litvaj.eshop.service;

import com.litvaj.eshop.exception.UserAlreadyExistException;
import com.litvaj.eshop.model.User;
import com.litvaj.eshop.model.request.UserDto;
import com.litvaj.eshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service for manipulations with user entities.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder bcryptEncoder;

    public User registerNewUser(UserDto userDto) throws UserAlreadyExistException {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("There is already a registered user with email address=" + userDto.getEmail());
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getName());
        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }
}
