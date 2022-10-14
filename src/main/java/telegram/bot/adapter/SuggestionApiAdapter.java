package telegram.bot.adapter;

import org.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Provides methods for calling boredapi rest API and retrieving an activity suggestion.
 */
public class SuggestionApiAdapter {
    private static SuggestionApiAdapter INSTANCE;

    private SuggestionApiAdapter() {
    }

    public static SuggestionApiAdapter getInstance() {
        if (INSTANCE == null) {
            synchronized (SuggestionApiAdapter.class) {
                INSTANCE = new SuggestionApiAdapter();
            }
        }

        return INSTANCE;
    }

    static final String MESSAGE_BASE_URL = "https://www.boredapi.com/api/activity";

    public String getActivity() {
        return getJsonResponse().getString("activity");
    }

    private JSONObject getJsonResponse() {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).version(HttpClient.Version.HTTP_2).build();
        UriBuilder builder = UriBuilder.fromUri(MESSAGE_BASE_URL);

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(String.valueOf(builder))).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("SuggestionApiAdapter " + response.body());

            return new JSONObject(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
