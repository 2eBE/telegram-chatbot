package telegram.bot.nlp;

import telegram.bot.adapter.WitAdapter;
import telegram.bot.model.UserMessage;
import telegram.bot.nlp.model.UserIntent;

/**
 * Retrieves user intent and sends it to the intent delegator.
 */
public class MessageProcessor {
    private WitAdapter intentProvider = WitAdapter.getInstance();
    private IntentDelegator intentDelegator = IntentDelegator.getInstance();

    public void process(UserMessage userMessage) {
        UserIntent userIntent = intentProvider.getIntent(userMessage.getMessageText())
                .map(intent -> new UserIntent(userMessage.getUser(), intent))
                .orElseGet(() -> new UserIntent(userMessage.getUser()));
        intentDelegator.process(userIntent);
    }
}
