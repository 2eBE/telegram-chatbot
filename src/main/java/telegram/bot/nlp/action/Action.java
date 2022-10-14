package telegram.bot.nlp.action;

import telegram.bot.nlp.model.UserIntent;

public interface Action {

    void execute(UserIntent intent);

}