package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.UserCreate;
import com.prog3.food_store_api.DTOs.UserDto;
import com.prog3.food_store_api.DTOs.UserEdit;
import com.prog3.food_store_api.DTOs.UserMapper;
import com.prog3.food_store_api.components.PasswordEncoderComponent;
import com.prog3.food_store_api.exceptions.ResourceNotFoundException;
import com.prog3.food_store_api.models.User;
import com.prog3.food_store_api.models.enums.Role;
import com.prog3.food_store_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoderComponent passwordEncoderComponent;

    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    public UserDto create(UserCreate dto) {
        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoderComponent.encode(dto.password()));
        user.setRole(Role.valueOf(dto.role()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto update(Long id, UserEdit dto) {
        User user = findEntityById(id);
        userMapper.updateFromDto(dto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        User user = findEntityById(id);
        user.setDeleted(true);
        userRepository.save(user);
    }

    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
