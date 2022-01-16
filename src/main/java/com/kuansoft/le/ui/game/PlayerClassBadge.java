package com.kuansoft.le.ui.game;

import com.kuansoft.le.game.PlayerClass;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Collection;

@CssImport("./themes/lastepochfiltergenerator/components/player-class-badge.css")
public class PlayerClassBadge extends Composite<Div> implements HasStyle {

    public PlayerClassBadge(PlayerClass playerClass) {
        addClassName("player-class-badge");
        getElement().setAttribute("player-class", playerClass.name().toLowerCase());
        getContent().setText(playerClass.getDisplayName());
    }

    public static HorizontalLayout createBadgeContainer(Collection<PlayerClass> playerClasses) {
        HorizontalLayout badgeContainer = new HorizontalLayout();
        badgeContainer.setSpacing(false);
        badgeContainer.setPadding(false);
        badgeContainer.getThemeList().add("spacing-s");
        playerClasses.stream()
                .map(PlayerClassBadge::new)
                .forEach(badgeContainer::add);
        return badgeContainer;
    }
}
