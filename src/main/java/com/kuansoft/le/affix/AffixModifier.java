package com.kuansoft.le.affix;

public enum AffixModifier {
    ADDED("Added", "Reduced"),
    INCREASED("Increased", "Decreased");

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
