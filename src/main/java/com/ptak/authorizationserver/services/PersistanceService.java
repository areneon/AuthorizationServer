package com.ptak.authorizationserver.services;

import com.ptak.authorizationserver.User.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class PersistanceService {
    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    RestTemplate restTemplate;

    @Value("${persistance.application.name}")
    private String persistanceApplicationName;

    protected User getUserByUsernameFromDatabase(String username) {

//        UserDTO userDTO= webClientBuilder.build()
//                .get()
//                .uri("http://localhost:8083/user/"+ username)
//                .retrieve()
//                .bodyToMono(UserDTO.class)
//                .block();
        log.info("pobieranie USERA: ");
        UserDTO userDTO = restTemplate.getForObject("http://" + persistanceApplicationName + "/user/"+ username, UserDTO.class);
        log.info("POBRANO USERA: {}", userDTO.getUsername());
        return userDTO.toUser();
    }
}
