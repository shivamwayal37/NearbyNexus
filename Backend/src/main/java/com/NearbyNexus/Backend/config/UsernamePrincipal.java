package com.NearbyNexus.Backend.config;

import java.security.Principal;

public class UsernamePrincipal implements Principal {
    private String email;

    public UsernamePrincipal(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return email;
    }
}
