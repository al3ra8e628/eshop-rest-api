package com.eshop.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Order(11)
@Component
public class ApiGateWayFilter implements GlobalFilter {

    @Value("${not.allowed.request.params.characters}")
    private String notAllowedCharacters;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        LOGGER.debug("ApiGateWayFilter triggered at {}", LocalDateTime.now().toString());

        final ServerHttpRequest request = exchange.getRequest();
            //ctrl + shift + alt + j
        LOGGER.debug("request method: {}", request.getMethod());
        LOGGER.debug("request url: {}", request.getPath());

        for (Map.Entry<String, List<String>> stringListEntry : request.getQueryParams().entrySet()) {
            for (String value : stringListEntry.getValue()) {
                LOGGER.debug("request param: key={}, value={}", stringListEntry.getKey() , value);

                for (char c : notAllowedCharacters.toCharArray()) {
                    if(value.contains(c+"")) {
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatusCode.valueOf(400));
                        LOGGER.warn("sql injection attach...!!!"); //security 100%
                        return response.setComplete();
                    }
                }
            }
        }

        for (Map.Entry<String, List<String>> stringListEntry : request.getHeaders().entrySet()) {
            for (String value : stringListEntry.getValue()) {
                LOGGER.debug("request headers: key={}, value={}", stringListEntry.getKey() , value);
            }
        }

        return chain.filter(exchange);
    }


}
