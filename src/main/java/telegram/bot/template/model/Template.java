package telegram.bot.template.model;

public class Template {
    private String type;
    private String text;

    public Template(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
