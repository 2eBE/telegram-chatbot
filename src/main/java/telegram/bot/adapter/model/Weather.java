package telegram.bot.adapter.model;

public class Weather {
    String weather;
    String weatherDescription;
    Temperature Temperature;

    public Weather(String weather, String weatherDescription, Temperature temperature) {
        this.weather = weather;
        this.weatherDescription = weatherDescription;
        Temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public telegram.bot.adapter.model.Temperature getTemperature() {
        return Temperature;
    }
}
