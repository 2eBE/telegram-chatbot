package telegram.bot.adapter.mapper;

import telegram.bot.nlp.model.IntentType;

import java.util.Optional;

public class IntentTypeMapper {
    private static IntentTypeMapper INSTANCE;

    private IntentTypeMapper() {
    }

    public static IntentTypeMapper getInstance() {
        if (INSTANCE == null) {
            synchronized (IntentTypeMapper.class) {
                INSTANCE = new IntentTypeMapper();
            }
        }

        return INSTANCE;
    }

    public Optional<IntentType> map(String witType) {
        switch (witType) {
            case "greeting":
                return Optional.of(IntentType.GREETING);
            case "get_help":
                return Optional.of(IntentType.HELP);
            case "get_suggestion":
                return Optional.of(IntentType.SUGGESTION);
            case "wit$get_weather":
                return Optional.of(IntentType.WEATHER);
            default:
                return Optional.empty();
        }
    }
}
