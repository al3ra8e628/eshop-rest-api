package com.example.eshop.service.services;


import com.example.contract.services.UserIdentityProvider;
import com.example.eshop.service.security.UserIdentityContext;
import com.example.modals.UserIdentity;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserIdentityProvider implements UserIdentityProvider {

    @Override
    public UserIdentity getCurrentUserIdentity() {
        return UserIdentityContext.getCurrentIdentity();
    }

}
