package telegram.bot.nlp;

import telegram.bot.nlp.action.Action;
import telegram.bot.nlp.action.UnrecognizedRequestAction;
import telegram.bot.nlp.model.UserIntent;

/**
 * Delegates the intent to the action responsible for that intent type.
 */
public class IntentDelegator {
    private static IntentDelegator INSTANCE;

    public static IntentDelegator getInstance() {
        if (INSTANCE == null) {
            synchronized (IntentDelegator.class) {
                INSTANCE = new IntentDelegator();
            }
        }

        return INSTANCE;
    }

    public void process(UserIntent userIntent) {
        Action action = null;
        if (userIntent.getIntent() != null) {
            action = ActionRegistry.actions.get(userIntent.getIntent().getIntentType());
        } else {
            action = new UnrecognizedRequestAction();
        }
        action.execute(userIntent);

    }

}
