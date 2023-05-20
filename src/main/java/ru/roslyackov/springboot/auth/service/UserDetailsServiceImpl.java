package ru.roslyackov.springboot.auth.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roslyackov.springboot.auth.entity.User;
import ru.roslyackov.springboot.auth.repository.UserRepository;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService { // Impl в названии класса означает "Implementation" - реализация

    private UserRepository userRepository; // доступ к БД

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (!userOptional.isPresent()) {
            userOptional = userRepository.findByEmail(username);
        }

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User Not Found with username or email: " + username);
        }

        return new UserDetailsImpl(userOptional.get());
    }
}

