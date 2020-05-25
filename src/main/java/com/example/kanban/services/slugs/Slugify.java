package com.example.kanban.services.slugs;

import com.example.kanban.services.slugs.ISlug;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class Slugify implements ISlug {

    @Override
    public String parse(String input) {
        if (isEmptyOrNull(input)) return "";

        String out = normalize(input);
        out = trim(out);
        out = out.toLowerCase();
        out = out.replace(" ", "-");

        return out;
    }

    private String normalize(String input) {

        input = input.replace("ł", "l").replace("Ł", "L");
        String result = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        result = result.replaceAll("[^a-zA-Z0-9\\s]", " ");

        return result;
    }

    private String trim(String input) {
        return input.replaceAll("\\s+", " ");
    }

    private boolean isEmptyOrNull(String input) {
        return (input == null || input.trim().length() == 0);
    }
}
