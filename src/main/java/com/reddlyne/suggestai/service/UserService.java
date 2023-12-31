package com.reddlyne.suggestai.service;

import com.reddlyne.suggestai.configuration.jwt.JwtTokenUtil;
import com.reddlyne.suggestai.controller.response.UserLoginResponse;
import com.reddlyne.suggestai.exception.RegistirationNotCompleted;
import com.reddlyne.suggestai.model.User;
import com.reddlyne.suggestai.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User registerUser(String login, String password, String email) {
        if (login == null || password == null) {
            throw new RegistirationNotCompleted("Username or Password not valid.");
        }

        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setLogin(login);
        user.setPassword(encryptedPassword);
        user.setEmail(email);

        User userFromDb = userRepository.save(user);
        return userFromDb;
    }

    public UserLoginResponse login(String login, String password) {
        User user = userRepository.findByLogin(login);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new UsernameNotFoundException("Wrong username or password.");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        return new UserLoginResponse(user.getLogin(), accessToken);
    }

}
