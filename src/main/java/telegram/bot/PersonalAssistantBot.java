package telegram.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.bot.config.BotConfiguration;
import telegram.bot.model.User;
import telegram.bot.model.UserMessage;
import telegram.bot.nlp.MessageProcessor;

public class PersonalAssistantBot extends TelegramLongPollingBot {
    private static PersonalAssistantBot INSTANCE;

    public PersonalAssistantBot() {
    }

    public static PersonalAssistantBot getInstance() {
        if (INSTANCE == null) {
            synchronized (PersonalAssistantBot.class) {
                INSTANCE = new PersonalAssistantBot();
            }
        }

        return INSTANCE;
    }

    public static MessageProcessor userMessageProcessor = new MessageProcessor();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getFirstName();
            UserMessage userMessage = new UserMessage(new User(chatId, userName), messageText);
            userMessageProcessor.process(userMessage);
        }
    }

    @Override
    public String getBotUsername() {
        return BotConfiguration.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConfiguration.BOT_TOKEN;
    }

    public void sendMessage(Long chatId, String messageText) {
        SendMessage message = SendMessage.builder().chatId(chatId).text(messageText).build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
