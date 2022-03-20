package com.kuansoft.le.affix;

public enum AffixModifier {
    ADDED("Added", "Reduced"),
    INCREASED("Increased", "Decreased"),
    MORE("More", "Less");

    private final String positive;
    private final String negative;

    AffixModifier(String positive, String negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public String getPositive() {
        return positive;
    }

    public String getNegative() {
        return negative;
    }
}
