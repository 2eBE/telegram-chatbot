package telegram.bot.adapter;

import org.json.JSONArray;
import org.json.JSONObject;
import telegram.bot.adapter.model.Temperature;
import telegram.bot.adapter.model.Weather;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Provides methods for calling fcc-weather-api.glitch.me rest API and retrieving weather and temperature data.
 */
public class WeatherApiAdapter {
    private static WeatherApiAdapter INSTANCE;

    private WeatherApiAdapter() {
    }

    public static WeatherApiAdapter getInstance() {
        if (INSTANCE == null) {
            synchronized (WeatherApiAdapter.class) {
                INSTANCE = new WeatherApiAdapter();
            }
        }

        return INSTANCE;
    }

    static final String WEATHER_API_BASE_URL = "https://fcc-weather-api.glitch.me/api/current";

    public Weather getWeather(BigDecimal latitude, BigDecimal longitude) {

        JSONObject response = getJsonResponse(latitude, longitude);
        JSONArray weatherLink = response.getJSONArray("weather");
        JSONObject main = response.getJSONObject("main");

        BigDecimal feelsLike = main.getBigDecimal("feels_like");
        BigDecimal temperature = main.getBigDecimal("temp");

        JSONObject weatherData = (JSONObject) weatherLink.get(0);
        String weatherMain = weatherData.getString("main");
        String weatherDescription = weatherData.getString("description");
        return new Weather(weatherMain, weatherDescription, new Temperature(feelsLike, temperature));
    }

    private JSONObject getJsonResponse(BigDecimal latitude, BigDecimal longitude) {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).version(HttpClient.Version.HTTP_2).build();

        Weather weather = null;
        UriBuilder builder = UriBuilder.fromUri(WEATHER_API_BASE_URL)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("limit", 1);

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.valueOf(builder))).build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("WeatherApiAdapter " + response.body());

            return new JSONObject(response.body());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

