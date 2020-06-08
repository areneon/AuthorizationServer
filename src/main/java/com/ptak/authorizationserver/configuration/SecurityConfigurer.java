package com.ptak.authorizationserver.configuration;

import com.ptak.authorizationserver.filter.JwtRequestFilter;
import com.ptak.authorizationserver.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
   private MyUserDetailsService myUserDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       auth.userDetailsService(myUserDetailService);
    }
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/security/login").permitAll()
            .antMatchers("/security/validate").permitAll()
            .anyRequest()
            .authenticated();

http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
}
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return  NoOpPasswordEncoder.getInstance();
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
