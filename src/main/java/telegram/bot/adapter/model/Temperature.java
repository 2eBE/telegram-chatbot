package telegram.bot.adapter.model;

import java.math.BigDecimal;

public class Temperature {
    BigDecimal feelsLike;
    BigDecimal current;

    public Temperature(BigDecimal feelsLike, BigDecimal current) {
        this.feelsLike = feelsLike;
        this.current = current;
    }

    public BigDecimal getFeelsLike() {
        return feelsLike;
    }

    public BigDecimal getCurrent() {
        return current;
    }
}
