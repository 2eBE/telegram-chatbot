package telegram.bot.adapter;

import org.json.JSONArray;
import org.json.JSONObject;
import telegram.bot.adapter.mapper.IntentTypeMapper;
import telegram.bot.nlp.model.Intent;
import telegram.bot.nlp.model.IntentParameter;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Provides methods for calling wit rest API and retrieving intent.
 */
public class WitAdapter {

    private IntentTypeMapper intentTypeMapper = IntentTypeMapper.getInstance();
    public static final String MESSAGE_BASE_URL = "https://api.wit.ai/message";
    public static final String ACCESS_TOKEN = "AKB7QEAY4JBKYHZDBTUQ2ESNVAT5ALUL";
    private static WitAdapter INSTANCE;

    private WitAdapter() {
    }

    public static WitAdapter getInstance() {
        if (INSTANCE == null) {
            synchronized (WitAdapter.class) {
                INSTANCE = new WitAdapter();
            }
        }

        return INSTANCE;
    }

    public Optional<Intent> getIntent(String message) {
        JSONObject response = getJsonResponse(message);
        JSONArray intents = (JSONArray) response.get("intents");
        if (intents.isEmpty()) {
            return Optional.empty();
        }

        return intentTypeMapper.map(getIntent(intents))
                .map(intentType -> new Intent(intentType, getEntities(response)));
    }

    private JSONObject getJsonResponse(String message) {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).version(HttpClient.Version.HTTP_2).build();
        UriBuilder builder = UriBuilder.fromUri(MESSAGE_BASE_URL).queryParam("q", message);

        HttpRequest request = HttpRequest.newBuilder().GET().setHeader("Authorization", "Bearer " + ACCESS_TOKEN).uri(URI.create(String.valueOf(builder))).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("WitIntentResolver " + response.body());

            return new JSONObject(response.body());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String getIntent(JSONArray intents) {
        BigDecimal maxConfidence = new BigDecimal(0);
        String intentWithMaxConfidence = null;
        for (Object intent : intents) {
            BigDecimal confidence = ((JSONObject) intent).getBigDecimal("confidence");
            if (confidence.compareTo(maxConfidence) > 0) {
                maxConfidence = confidence;
                intentWithMaxConfidence = ((JSONObject) intent).getString("name");
            }
        }
        return intentWithMaxConfidence;
    }

    private Map<IntentParameter, Object> getEntities(JSONObject responseAsJSON) {
        Map<IntentParameter, Object> resolvedEntities = new HashMap<>();
        JSONObject entities = responseAsJSON.getJSONObject("entities");
        if (entities.isEmpty()) {
            return Collections.emptyMap();
        }

        if (!entities.isNull("wit$location:location")) {
            resolvedEntities.putAll(getLocationData(entities));
        }
        return resolvedEntities;
    }

    private Map<IntentParameter, Object> getLocationData(JSONObject entities) {
        Map<IntentParameter, Object> locationData = new HashMap<>();
        JSONArray locations = entities.getJSONArray("wit$location:location");
        JSONObject location = (JSONObject) locations.get(0);

        if (!location.isNull("resolved") && !location.getJSONObject("resolved").isEmpty()) {
            JSONObject resolved = location.getJSONObject("resolved");
            JSONArray values = resolved.getJSONArray("values");
            for (Object value : values) {
                JSONObject valueAsJSON = (JSONObject) value;
                if (valueAsJSON.getString("domain").equals("locality")) {
                    locationData.put(IntentParameter.CITY_NAME, valueAsJSON.get("name"));
                    JSONObject coords = valueAsJSON.getJSONObject("coords");
                    locationData.put(IntentParameter.LATITUDE, coords.getBigDecimal("lat"));
                    locationData.put(IntentParameter.LONGITUDE, coords.getBigDecimal("long"));
                }
            }
        }
        return locationData;
    }

}
