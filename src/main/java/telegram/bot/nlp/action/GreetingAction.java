package telegram.bot.nlp.action;

import telegram.bot.PersonalAssistantBot;
import telegram.bot.model.User;
import telegram.bot.nlp.model.IntentType;
import telegram.bot.nlp.model.UserIntent;
import telegram.bot.template.TemplateProvider;

import java.util.HashMap;
import java.util.Map;

public class GreetingAction implements Action {
    TemplateProvider templateProvider = TemplateProvider.getInstance();
    PersonalAssistantBot bot = PersonalAssistantBot.getInstance();

    @Override
    public void execute(UserIntent userIntent) {
        Map<String, Object> params = new HashMap<>();
        User user = userIntent.getUser();
        params.put("userName", user.getUserName());

        templateProvider.getAnyTemplate(IntentType.GREETING.toString())
                .map(template -> templateProvider.formatText(template, params))
                .ifPresent(formattedText -> bot.sendMessage(user.getChatId(), formattedText));
    }
}
