package com.asan.service;

import com.asan.model.ApplicationUser;
import com.asan.model.ApplicationUserRole;
import com.asan.repository.ApplicationUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;
    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(applicationUser.getUsername(),
                        applicationUser.getPassword(),
                        getGrantedAuthorities(applicationUser));
    }

    private List<GrantedAuthority> getGrantedAuthorities(ApplicationUser applicationUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (ApplicationUserRole applicationUserRole:applicationUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(applicationUserRole.getRole()));
        }
        return authorities;
    }
}
