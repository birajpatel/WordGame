package com.birin.wordgame.core.words;

import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 Created by Biraj on 9/13/16.
 */
public class WordsParserTest {

    private static final String CORRECT_FORMAT = "[\n" +
                                                 "  {\n" +
                                                 "    \"text_eng\": \"primary school\",\n" +
                                                 "    \"text_spa\": \"escuela primaria\"\n" +
                                                 "  },\n" +
                                                 "  {\n" +
                                                 "    \"text_eng\": \"teacher\",\n" +
                                                 "    \"text_spa\": \"profesor / profesora\"\n" +
                                                 "  }]";

    private WordsParser parser;

    @Rule public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        parser = new WordsParser();
    }

    @Test
    public void shouldThrowExceptionOnWrongResponseFormat() {
        expectedException.expect(JsonSyntaxException.class);
        parser.parse("hello world !");
    }

    @Test
    public void shouldParseDataOnCorrectResponseFormat() {
        List<WordEntity> entities = parser.parse(CORRECT_FORMAT);
        assertThat(entities.size(), is(equalTo(2)));

        WordEntity first = entities.get(0);
        assertThat("primary school", is(equalTo(first.getEnglishText())));
        assertThat("escuela primaria", is(equalTo(first.getSpanishText())));

        WordEntity second = entities.get(1);
        assertThat("teacher", is(equalTo(second.getEnglishText())));
        assertThat("profesor / profesora", is(equalTo(second.getSpanishText())));
    }

}