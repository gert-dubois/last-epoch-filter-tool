package com.kuansoft.le.game;

public enum Tag implements HasDisplayName {
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

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
