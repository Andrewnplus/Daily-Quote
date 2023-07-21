package com.example.tool;

import com.example.model.Quote;
import com.example.model.QuoteCategory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class QuoteDeserializer extends StdDeserializer<Quote> {

    public QuoteDeserializer() {
        this(null);
    }

    public QuoteDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Quote deserialize(final JsonParser jp,final  DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        String text = node.get("text").asText();
        String author = node.get("author").asText();
        String categoryString = node.get("category").asText();
        QuoteCategory category = QuoteCategory.fromString(categoryString);

        Quote quote = new Quote();
        quote.setText(text);
        quote.setAuthor(author);
        quote.setCategory(category);

        return quote;
    }
}

