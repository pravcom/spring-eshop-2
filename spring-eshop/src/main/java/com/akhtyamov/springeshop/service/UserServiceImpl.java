package com.akhtyamov.springeshop.service;

import com.akhtyamov.springeshop.dao.UserRepository;
import com.akhtyamov.springeshop.domain.Role;
import com.akhtyamov.springeshop.domain.User;
import com.akhtyamov.springeshop.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    @Override
//    @LogExecutionTime(additionalMessage = "This is a save Method")
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchingPassword())) {
            throw new RuntimeException("Password is not equals");
        }

        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(Role.CLIENT)
                .activateCode(UUID.randomUUID().toString())
                .build();

        this.save(user);
        return true;
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
        if(user.getActivateCode() != null && !user.getActivateCode().isEmpty()){
            mailSenderService.sendActivateCode(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with name " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles
        );
    }

    @Override
//    @LogExecutionTime(additionalMessage = "This is a Get Method")
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }
    private UserDTO toDto(User user) {
        return UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
//    @LogExecutionTime(additionalMessage = "This is a GetName Method")
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    @Transactional
//    @LogExecutionTime(additionalMessage = "This is a Update Method")
    public void updateProfile(UserDTO userDTO) {
        User savedUser = userRepository.findFirstByName(userDTO.getUsername());
        if (savedUser == null){
            throw new RuntimeException("User not found by name " + userDTO.getUsername() );
        }

        boolean isChanged = false;

        if (userDTO.getPassword()!=null && !userDTO.getPassword().isEmpty()){
            savedUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if (!Objects.equals(userDTO.getEmail(),savedUser.getEmail())){
            savedUser.setEmail(userDTO.getEmail());
            isChanged=true;
        }

        if (isChanged){
            userRepository.save(savedUser);
        }
    }

    @Override
    @Transactional
    public boolean activateUser(String activateCode) {
        if(activateCode == null || activateCode.isEmpty()){
            return false;
        }
        User user = userRepository.findFirstByActivateCode(activateCode);
        if(user == null){
            return false;
        }

        user.setActivateCode(null);
        userRepository.save(user);

        return true;
    }
}
