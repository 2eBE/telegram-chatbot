package telegram.bot.template;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.text.StringSubstitutor;
import telegram.bot.template.model.Template;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class TemplateProvider {

    private static TemplateProvider INSTANCE;
    private static List<Template> templates = new ArrayList<>();
    private static final String FILE_NAME = "templates.csv";
    private final Random random = new Random();

    private TemplateProvider() {
    }

    public static TemplateProvider getInstance() {
        if (INSTANCE == null) {
            readTemplatesFromCsv(FILE_NAME);
            INSTANCE = new TemplateProvider();
        }

        return INSTANCE;
    }

    public List<Template> getTemplates(String type) {
        return templates.stream().filter(template -> template.getType().equals(type)).collect(Collectors.toList());
    }

    public Optional<Template> getAnyTemplate(String type) {
        List<Template> templatesForType = getTemplates(type.toString());
        return templatesForType.stream()
                .skip(random.nextInt(templatesForType.size()))
                .findFirst();
    }

    public String formatText(Template template, Map<String, Object> params) {
        return StringSubstitutor.replace(template.getText(), params, "{", "}");
    }

    private static void readTemplatesFromCsv(String fileName) {
        CSVReader reader = null;
        try {
            URL resource = TemplateProvider.class.getClassLoader().getResource(fileName);
            reader = new CSVReader(new FileReader(new File(resource.toURI())));
            String[] line;
            while ((line = reader.readNext()) != null) {
                templates.add(createTemplate(line));
            }
        } catch (CsvValidationException | IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    private static Template createTemplate(String[] metadata) {
        final String type = metadata[0];
        final String text = metadata[1];
        return new Template(type, text);
    }
}
