package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.AuthResponse;
import com.prog3.food_store_api.DTOs.UserCreate;
import com.prog3.food_store_api.DTOs.UserDto;
import com.prog3.food_store_api.DTOs.UserLogin;
import com.prog3.food_store_api.DTOs.UserMapper;
import com.prog3.food_store_api.components.PasswordEncoderComponent;
import com.prog3.food_store_api.exceptions.BusinessException;
import com.prog3.food_store_api.exceptions.InvalidCredentialsException;
import com.prog3.food_store_api.models.User;
import com.prog3.food_store_api.models.enums.Role;
import com.prog3.food_store_api.repositories.UserRepository;
import com.prog3.food_store_api.security.JwtService;
import com.prog3.food_store_api.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoderComponent passwordEncoderComponent;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Transactional
    public AuthResponse register(UserCreate dto) {
        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new BusinessException("Invalid params");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoderComponent.encode(dto.password()));

        Role role = Role.CLIENT;
        if (dto.role() != null) {
            try { role = Role.valueOf(dto.role().toUpperCase()); } catch (IllegalArgumentException ignored) {}
        }
        user.setRole(role);

        User saved = userRepository.save(user);
        UserDto userDto = userMapper.toDto(saved);
        String token = jwtService.generateToken(userDetailsService.toUserDetails(saved));

        return new AuthResponse(token, userDto);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(UserLogin dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoderComponent.matches(dto.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        UserDto userDto = userMapper.toDto(user);
        String token = jwtService.generateToken(userDetailsService.toUserDetails(user));

        return new AuthResponse(token, userDto);
    }
}
