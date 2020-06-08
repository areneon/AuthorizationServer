package com.ptak.authorizationserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticatonResponse {
    private final String jwt;

}
