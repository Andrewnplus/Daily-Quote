package com.example.tool;

import com.example.model.QuoteCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class QuoteCategoryConverter implements AttributeConverter<QuoteCategory, String> {

    @Override
    public String convertToDatabaseColumn(final QuoteCategory category) {
        if (category == null) {
            return null;
        }
        return category.name().toLowerCase();
    }

    @Override
    public QuoteCategory convertToEntityAttribute(final String dbData) {
        if (dbData == null) {
            return null;
        }
        return QuoteCategory.valueOf(dbData.toUpperCase());
    }
}

