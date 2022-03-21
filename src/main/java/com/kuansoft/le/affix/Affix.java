package com.kuansoft.le.affix;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuansoft.le.game.HasDisplayName;
import com.kuansoft.le.game.HasId;
import com.kuansoft.le.game.PlayerClass;
import com.kuansoft.le.game.Tag;

import java.util.Set;

public class Affix implements HasDisplayName, HasId {

    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "classes")
    private Set<PlayerClass> classes;

    @JsonProperty(value = "rollsOnIdols")
    private boolean rollsOnIdol;

    @JsonProperty(value = "modifier")
    private AffixModifier modifier;

    @JsonProperty(value = "tags")
    private Set<Tag> tags;

    @JsonProperty("negative")
    private boolean negative;

    @Override
    @JsonIgnore
    public String getDisplayName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    public boolean isRollsOnIdols() {
        return rollsOnIdol;
    }

    @JsonIgnore
    public String getModifier() {
        AffixModifier modifierType = getModifierType();
        return isNegative() ? modifierType.getNegative() : modifierType.getPositive();
    }

    private AffixModifier getModifierType() {
        return modifier;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public boolean isNegative() {
        return negative;
    }

    public Set<PlayerClass> getClassRequirements() {
        return classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Affix affix = (Affix) o;

        return id == affix.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
