package com.example.search.engine.builder;

import com.example.search.engine.model.WordMatch;
import org.springframework.util.Assert;

public class WordMatchBuilder {

    private String word;
    private Integer milestone;
    private String content;

    public WordMatchBuilder word(String word) {
        this.word = word;
        return this;
    }

    public WordMatchBuilder milestone(Integer milestone) {
        this.milestone = milestone;
        return this;
    }

    public WordMatchBuilder content(String content) {
        this.content = content;
        return this;
    }

    public WordMatch build() {
        Assert.hasText(word, "wordSupplier must not be null nor empty");
        Assert.notNull(milestone, "milestoneSupplier must not be null");
        Assert.notNull(content, "contentSupplier must not be null");

        return new WordMatch(word, content.indexOf(word, milestone));
    }
}
