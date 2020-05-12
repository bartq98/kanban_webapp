package com.example.kanban.services.slugs;

import com.github.slugify.Slugify;

public class LibSlugify implements ISlug {

    @Override
    public String parse(String input) {
        Slugify slug = new Slugify();
        return slug.slugify(input);
    }
}
