package com.kuansoft.le.game;

public enum PlayerClass implements HasDisplayName {
    PRIMALIST("Primalist"),
    MAGE("Mage"),
    SENTINEL("Sentinel"),
    ACOLYTE("Acolyte"),
    ROGUE("Rogue");

    private final String name;

    PlayerClass(String name) {

        this.name = name;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}
