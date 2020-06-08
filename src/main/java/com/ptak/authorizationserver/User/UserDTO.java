package com.ptak.authorizationserver.User;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    private boolean isEnabled;

    private List<String> roles;

public UserDTO(){

}
    public User toUser(){
        return new User(this.username, this.password, this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}
