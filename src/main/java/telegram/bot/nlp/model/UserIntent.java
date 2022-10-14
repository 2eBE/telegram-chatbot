package telegram.bot.nlp.model;

import telegram.bot.model.User;

public class UserIntent {
    User user;
    Intent intent;

    public UserIntent(User user) {
        this.user = user;
        this.intent = intent;
    }
    public UserIntent(User user, Intent intent) {
        this.user = user;
        this.intent = intent;
    }

    public User getUser() {
        return user;
    }

    public Intent getIntent() {
        return intent;
    }
}
