package org.rifasya.main.services;


import org.rifasya.main.entities.User;
import org.rifasya.main.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Buscar por username o email
        User user = userRepository.findByUser(usernameOrEmail)
                .or(() -> userRepository.findByMail(usernameOrEmail))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + usernameOrEmail));

        // Convertir a UserDetails
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUser())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}

