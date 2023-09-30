package com.example.eshop.service.security;

import com.example.modals.UserIdentity;

public class UserIdentityContext {
    private static final ThreadLocal<UserIdentity> userIdentityContext = new ThreadLocal<>();

    public static void setCurrentIdentity(UserIdentity userIdentity) {
        userIdentityContext.set(userIdentity);
    }

    public static UserIdentity getCurrentIdentity() {
        return userIdentityContext.get();
    }

}
