package com.example.mappers;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public interface MoneyMapper {

    default BigDecimal valueFromMoney(Money money) {
        return Optional.ofNullable(money)
                .map(Money::getAmount)
                .orElse(null);
    }

    default String currencyFromMoney(Money money) {
        return Optional.ofNullable(money)
                .map(Money::getCurrencyUnit)
                .map(CurrencyUnit::getCode)
                .orElse(null);
    }

    default Money toMoney(BigDecimal amount, String currencyCode) {
        if (Objects.isNull(amount) || Objects.isNull(currencyCode)) {
            return null;
        }

        return Money.of(CurrencyUnit.of(currencyCode), amount);
    }


}
