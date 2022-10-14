package telegram.bot.model;

public class User {
    Long chatId;

    String userName;

    public User(Long chatId, String userName) {
        this.chatId = chatId;
        this.userName = userName;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getUserName() {
        return userName;
    }
}
