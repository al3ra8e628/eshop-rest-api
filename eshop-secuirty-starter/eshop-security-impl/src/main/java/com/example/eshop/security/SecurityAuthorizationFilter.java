package com.example.eshop.security;

import com.example.eshop.models.UserIdentity;
import com.example.eshop.models.UserIdentity.*;
import com.jayway.jsonpath.JsonPath;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Order(0)
@Component
public class SecurityAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        final String authorizationHeader = httpRequest.getHeader("Authorization");

        if (Strings.isBlank(authorizationHeader)) {
            LOGGER.warn("Authorization Header is Required!!");
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        final UserDetails userDetails = toUserDetails(authorizationHeader);

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(httpRequest));

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        chain.doFilter(httpRequest, httpResponse);
    }

    private UserDetails toUserDetails(String authorizationHeader) {
        try {
            authorizationHeader = authorizationHeader.replace("Bearer ", "");

            /*
            * alg details    base 64
            * .
            * claims   base 64
            * .
            * token details  base 64
            * */

            byte[] decodedUserDetailsSection = Base64.getDecoder().decode(
                    authorizationHeader.split("\\.")[1]);

            String userDetails = new String(decodedUserDetailsSection);

            final String username = readValue(userDetails, "$.username");
            final String fullName = readValue(userDetails, "$.fullName");
            final String email = readValue(userDetails, "$.email");

            final List<String> userRoles = readValue(userDetails, "$.role");

            //the user roles in the spring security type.
            final List<GrantedAuthority> authorities = new ArrayList<>();


            for (String userRole : Optional.ofNullable(userRoles)
                    .orElse(new ArrayList<>())) {
                authorities.add(new SimpleGrantedAuthority(userRole));
            }

            UserIdentityContext.setCurrentIdentity(UserIdentity.of(
                    fullName,
                    username,
                    email,
                    userRoles
            ));

            return new User(username, "", authorities);
        } catch (Exception e) {
            throw new RuntimeException("invalid token structure!!!");
        }
    }

    private <T> T readValue(String json, String jsonPath) {
        try {
            return JsonPath.read(json, jsonPath);
        } catch (Exception e) {
            LOGGER.warn("path {}: is not exists", jsonPath);
            return null;
        }
    }

}
