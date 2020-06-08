package com.ptak.authorizationserver.model;

import com.ptak.authorizationserver.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TokenValidateResponse {

   private UserDTO user;
   private boolean isValid;

    public TokenValidateResponse(){}
}
