package telegram.bot.nlp;

import telegram.bot.nlp.action.*;
import telegram.bot.nlp.model.IntentType;

import java.util.HashMap;
import java.util.Map;

public class ActionRegistry {
    public static final Map<IntentType, Action> actions = new HashMap<>();

    static {
        actions.put(IntentType.GREETING, new GreetingAction());
        actions.put(IntentType.HELP, new HelpAction());
        actions.put(IntentType.SUGGESTION, new SuggestionAction());
        actions.put(IntentType.WEATHER, new WeatherAction());
    }
}
