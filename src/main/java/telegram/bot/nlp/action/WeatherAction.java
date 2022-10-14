package telegram.bot.nlp.action;

import telegram.bot.PersonalAssistantBot;
import telegram.bot.adapter.WeatherApiAdapter;
import telegram.bot.adapter.model.Weather;
import telegram.bot.nlp.model.Intent;
import telegram.bot.nlp.model.IntentParameter;
import telegram.bot.nlp.model.IntentType;
import telegram.bot.nlp.model.UserIntent;
import telegram.bot.template.TemplateProvider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WeatherAction implements Action {
    TemplateProvider templateProvider = TemplateProvider.getInstance();
    PersonalAssistantBot bot = PersonalAssistantBot.getInstance();
    WeatherApiAdapter weatherApiAdapter = WeatherApiAdapter.getInstance();

    @Override
    public void execute(UserIntent userIntent) {
        Intent intent = userIntent.getIntent();
        String cityName = (String) intent.getContext().get(IntentParameter.CITY_NAME);
        BigDecimal latitude = (BigDecimal) intent.getContext().get(IntentParameter.LATITUDE);
        BigDecimal longitude = (BigDecimal) intent.getContext().get(IntentParameter.LONGITUDE);

        if (cityName == null || latitude == null || longitude == null ){
            templateProvider.getAnyTemplate("WEATHER_UNKNOWN_CITY")
                    .ifPresent(template -> bot.sendMessage(userIntent.getUser().getChatId(), template.getText()));
            return;
        }

        Weather weather = weatherApiAdapter.getWeather(latitude, longitude);
        Map<String, Object> params = new HashMap<>();
        params.put("cityName", cityName);
        params.put("weatherDescription", weather.getWeatherDescription());
        params.put("temperature", weather.getTemperature().getCurrent());
        params.put("feelsLike", weather.getTemperature().getFeelsLike());

        templateProvider.getAnyTemplate(IntentType.WEATHER.toString())
                .map(template -> templateProvider.formatText(template, params))
                .ifPresent(formattedText -> bot.sendMessage(userIntent.getUser().getChatId(), formattedText));

    }
}
