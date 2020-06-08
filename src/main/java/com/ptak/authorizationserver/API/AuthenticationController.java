package com.ptak.authorizationserver.API;

import com.ptak.authorizationserver.User.UserDTO;
import com.ptak.authorizationserver.Util.JwtUtil;
import com.ptak.authorizationserver.model.AuthenticationRequest;
import com.ptak.authorizationserver.model.AuthenticatonResponse;
import com.ptak.authorizationserver.model.TokenValidateRequest;
import com.ptak.authorizationserver.model.TokenValidateResponse;
import com.ptak.authorizationserver.services.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("security")
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity test(){
        return  ResponseEntity.ok("Gitara");
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            log.info("LOGIN I HASłO: {}, {}",authenticationRequest.getUsername(), authenticationRequest.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        log.info("ROLE UŻYTKOWNIKA: {}", userDetails.getAuthorities().toString());
    String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticatonResponse(jwt));
    }

    @CrossOrigin("*")
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ResponseEntity validateToken(@RequestBody TokenValidateRequest tokenValidateRequest) throws Exception {
log.info("WESZLO W VALIDATE CONTROLLER");
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenValidateRequest.getUsername());
        boolean isValid = jwtUtil.validateToken(tokenValidateRequest.getToken(), userDetails);
        log.info("userdetails {}",  userDetails.getUsername());
        return ResponseEntity.ok(new TokenValidateResponse(new UserDTO(userDetails.getUsername(), userDetails.getPassword(), userDetails.isEnabled(), userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList())), isValid));
    }

}
