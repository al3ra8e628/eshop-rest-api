package com.example.eshop.security;


import com.example.eshop.contract.UserIdentityProvider;
import com.example.eshop.models.UserIdentity;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserIdentityProvider implements UserIdentityProvider {

    @Override
    public UserIdentity getCurrentUserIdentity() {
        return UserIdentityContext.getCurrentIdentity();
    }

}
