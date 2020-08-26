package com.litvaj.eshop.service;

import com.litvaj.eshop.model.User;
import com.litvaj.eshop.model.request.UserDto;
import com.litvaj.eshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @SpyBean
    UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder bcryptEncoder;

    @Test
    public void shouldSaveUser() {
        final String name = "name";
        final String pass = "password";
        final String enc_pass = "enc_password";
        final String email = "john@gmail.com";

        UserDto userDto = new UserDto();
        userDto.setName(name);
        userDto.setPassword(pass);
        userDto.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(bcryptEncoder.encode(pass)).thenReturn(enc_pass);

        userService.registerNewUser(userDto);

        verify(bcryptEncoder, times(1)).encode(pass);

        User user = new User();
        user.setUsername(name);
        user.setPassword(enc_pass);
        user.setEmail(email);

        verify(userRepository, times(1)).save(user);
    }
}
