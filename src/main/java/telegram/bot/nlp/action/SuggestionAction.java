package telegram.bot.nlp.action;

import telegram.bot.PersonalAssistantBot;
import telegram.bot.adapter.SuggestionApiAdapter;
import telegram.bot.nlp.model.UserIntent;

public class SuggestionAction implements Action {
    PersonalAssistantBot bot = PersonalAssistantBot.getInstance();
    SuggestionApiAdapter suggestionApiAdapter = SuggestionApiAdapter.getInstance();

    @Override
    public void execute(UserIntent userIntent) {
        String activity = suggestionApiAdapter.getActivity();
        bot.sendMessage(userIntent.getUser().getChatId(), activity);
    }
}
