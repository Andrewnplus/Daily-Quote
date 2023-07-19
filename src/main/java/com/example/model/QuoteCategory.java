package com.example.model;

public enum QuoteCategory {
    GOD("god"), LOVE("love"), LIFE("life"), HUMOR("humor");

    private final String category;

    QuoteCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public static QuoteCategory fromString(String value) {
        for (QuoteCategory category : QuoteCategory.values()) {
            if (category.getCategory().equalsIgnoreCase(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("invalid category: " + value);
    }
}
