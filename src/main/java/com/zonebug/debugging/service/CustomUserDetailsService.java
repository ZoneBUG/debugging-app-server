package com.zonebug.debugging.service;

import com.zonebug.debugging.domain.user.User;
import com.zonebug.debugging.domain.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    public final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        return userRepository.findByEmail(email)
                .map(user -> createUser(email, user))
                .orElseThrow(() -> new UsernameNotFoundException(email + " -> DB에서 찾을 수 없음"));
    }

    private org.springframework.security.core.userdetails.User createUser(String email, User user) {


        List authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

}
