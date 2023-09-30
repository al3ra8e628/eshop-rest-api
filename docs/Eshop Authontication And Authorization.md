##Authentication And Authorization.

##1- Authentication

##Identity Management Solutions:
1. Microsoft Azure Active Directory.
2. AWS Services.
3. AWS Services.
4. Keycloak.


###OAuth2 Identity Management.
- create users.
- create roles.
- assign user with roles.
- generate jwt signed token.


###keycloak integration:

- docker-compose
```yaml
version: '3'

services:
  keycloak:
    image: jboss/keycloak:15.0.0
    environment:
      DB_VENDOR: H2
      DB_DATABASE: keycloakdb
      DB_USER: keycloakdb
      DB_PASSWORD: keycloakdb
      DB_SCHEMA: public
      KEYCLOAK_USER: admin
      KEYCLOAK_PASSWORD: admin
    ports:
      - 9000:8080
```
- configure keycloak realm, client, users, roles by following this article.  
  https://czetsuya.medium.com/generate-keycloak-bearer-token-using-postman-5bd81d7d1f8


###API Gateway:
- add support for OAuth2 signed token validation.


integration dependency...
```xml 
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>
```

required property
```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://127.0.0.1:9000/auth/realms/eshop-realm 
```

security configuration class
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(it -> it.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(JwtDecoders.fromIssuerLocation(issuerUri))))
                .authorizeHttpRequests(it ->
                        it.anyRequest().authenticated())
                .build();
    }
}
```




##2- Authorization
- at the service that requires authorization couple of things need to be done:

1. enable security filter with no login required.
2. add filter to parse the provided authorization token and map it to user identity object,
3. inject the token roles into spring security context.
4. add allowed roles annotation to the authorized endpoints.
5. handle the internal calls in the filter.
6. add get current identity service.

use the thread context to add thread related variables (user details) to be used at any time.


```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```


```java
@Component
public class SecurityAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = httpServletRequest.getHeader("Authorization");

        if (Strings.isBlank(token)) {
            httpServletResponse.setStatus(403);
            return;
        }

        UserDetails userDetails = getUserDetails(token.replaceAll("Bearer ", ""));

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                null,
                userDetails.getAuthorities());

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @SneakyThrows
    private UserDetails getUserDetails(String token) {
        final String[] tokenSections = token.split("\\.");

        final String tokenPayloadSection = new String(Base64.getDecoder().decode(tokenSections[1]),
                StandardCharsets.UTF_8);

        final String userName = JsonPath.read(tokenPayloadSection, "$.name");
        final List<String> roles = JsonPath.read(tokenPayloadSection, "$.realm_access.roles");

        return buildUserDetails(userName, roles.stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    private User buildUserDetails(String username, List<GrantedAuthority> authorities) {
        return new User(username, "", authorities);
    }


}
```



```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(it -> {
                    it.requestMatchers("/v1/*").denyAll();
                    it.anyRequest().permitAll();
                })
                .addFilterBefore(new SecurityAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}
```