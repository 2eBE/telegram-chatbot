package telegram.bot.nlp.action;

import telegram.bot.PersonalAssistantBot;
import telegram.bot.nlp.model.UserIntent;
import telegram.bot.template.TemplateProvider;

public class UnrecognizedRequestAction implements Action{
    TemplateProvider templateProvider = TemplateProvider.getInstance();
    PersonalAssistantBot bot = PersonalAssistantBot.getInstance();

    @Override
    public void execute(UserIntent userIntent) {
        templateProvider.getAnyTemplate("UNRECOGNIZED_REQUEST")
                .ifPresent(template -> bot.sendMessage(userIntent.getUser().getChatId(), template.getText()));
    }
}
