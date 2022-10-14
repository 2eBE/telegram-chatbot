package telegram.bot.model;

public class UserMessage {
    User user;
    String messageText;

    public UserMessage(User user, String messageText) {
        this.user = user;
        this.messageText = messageText;
    }

    public User getUser() {
        return user;
    }

    public String getMessageText() {
        return messageText;
    }
}
