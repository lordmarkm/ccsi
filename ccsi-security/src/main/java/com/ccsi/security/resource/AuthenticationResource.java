package com.ccsi.security.resource;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

    @RequestMapping(method = GET)
    public ResponseEntity<Principal> getAuth(Principal principal) {
        return new ResponseEntity<>(principal, OK);
    }

}
