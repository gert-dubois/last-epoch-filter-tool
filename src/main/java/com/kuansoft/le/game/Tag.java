package com.kuansoft.le.game;

public enum Tag {
    MISCELLANEOUS("Miscellaneous"),
    PHYSICAL("Physical"),
    LIGHTNING("Lightning"),
    COLD("Cold"),
    FIRE("Fire"),
    VOID("Void"),
    NECROTIC("Necrotic"),
    POISON("Poison"),
    ELEMENTAL("Elemental"),
    SPELL("Spell"),
    MELEE("Melee"),
    THROWING("Throwing"),
    BOW("Bow"),
    DOT("Damage over Time"),
    MINION("Minion"),
    TOTEM("Totem"),
    CHANNELING("Channeling"),
    TRANSFORMATION("Transformation"),
    ON_HIT("On Hit"),
    ON_KILL("On Kill"),
    RESISTANCE("Resistance"),
    HEALTH("Health"),
    BLOCK("Block"),
    DODGE("Dodge"),
    ENDURANCE("Endurance"),
    CRITICAL_STRIKE("Critical Strike"),
    MANA("Mana"),
    ATTRIBUTES("Attributes"),
    ARMOR("Armour"),
    SKILL_LEVEL("Level of Skills"),
    WARD("Ward");

    private final String displayName;

    Tag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Tag fromIndex(int tagIndex) {
        return switch (tagIndex) {
            case 0 -> MISCELLANEOUS;
            case 1 -> PHYSICAL;
            case 2 -> LIGHTNING;
            case 3 -> COLD;
            case 4 -> FIRE;
            case 5 -> VOID;
            case 6 -> NECROTIC;
            case 7 -> POISON;
            case 8 -> ELEMENTAL;
            case 9 -> SPELL;
            case 10 -> MELEE;
            case 11 -> THROWING;
            case 12 -> BOW;
            case 13 -> DOT;
            case 14 -> MINION;
            case 15 -> TOTEM;
            case 19 -> CHANNELING;
            case 20 -> TRANSFORMATION;
            default -> MISCELLANEOUS;
        };
    }
}
