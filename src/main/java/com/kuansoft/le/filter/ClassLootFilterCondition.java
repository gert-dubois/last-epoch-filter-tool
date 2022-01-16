package com.kuansoft.le.filter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.game.PlayerClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassLootFilterCondition extends LootFilterCondition {

    @JsonProperty
    private List<String> classes = new ArrayList<>();

    private ClassLootFilterCondition() {
        super("ClassCondition");
    }

    public static ClassLootFilterCondition create() {
        return new ClassLootFilterCondition();
    }

    public List<String> getClasses() {
        return classes;
    }

    public ClassLootFilterCondition addClasses(Collection<PlayerClass> classes) {
        classes.stream().map(PlayerClass::getDisplayName).forEach(this.classes::add);
        return this;
    }
}
