package com.santiago.inventory.service;

import com.santiago.inventory.entity.User;
import com.santiago.inventory.entity.Role;
import com.santiago.inventory.repository.RoleRepository;
import com.santiago.inventory.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
    public void createUser(
            String username,
            String password,
            String roleName) {

        User user = new User();

        user.setUsername(username);

        user.setPassword(
                passwordEncoder.encode(password)
        );

        Role role = roleRepository
                .findByName(roleName)
                .orElseThrow();

        user.getRoles().add(role);

        userRepository.save(user);
    }
    public boolean usernameExists(String username) {

        return userRepository.existsByUsername(username);
    }
    public long countUsers() {
        return userRepository.count();
    }
}

