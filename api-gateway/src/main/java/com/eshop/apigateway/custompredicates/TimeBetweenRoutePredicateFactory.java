package com.eshop.apigateway.custompredicates;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenRoutePredicateFactory.Config> {
    public static final String DATETIME_KEY = "datetime";

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(DATETIME_KEY);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenRoutePredicateFactory.Config config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                final String timeWithZone = config.getBetweenTimeWithZone();
                final String zoneId = timeWithZone.split(",")[2];

                LocalTime currentTime = LocalTime.now(ZoneId.of(zoneId));

                LocalTime firstTime = LocalTime.parse(timeWithZone.split(",")[0]);
                LocalTime secondTime = LocalTime.parse(timeWithZone.split(",")[1]);

                return firstTime.isBefore(currentTime) && secondTime.isAfter(currentTime);
            }

            @Override
            public Object getConfig() {
                return config;
            }

            @Override
            public String toString() {
                return String.format("BetweenTime: %s", config.getBetweenTimeWithZone());
            }
        };
    }

    public static class Config {
        private String betweenTimeWithZone;

        public String getBetweenTimeWithZone() {
            return betweenTimeWithZone;
        }

        public void setBetweenTimeWithZone(String betweenTimeWithZone) {
            this.betweenTimeWithZone = betweenTimeWithZone;
        }

    }

}
