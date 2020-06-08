package com.ptak.authorizationserver.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    PersistanceService persistanceService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("WYWOLANO dla uzytkownika: {}", username);
       // return new User("cyga", "password", new ArrayList<>());
        return persistanceService.getUserByUsernameFromDatabase(username);
    }
}
