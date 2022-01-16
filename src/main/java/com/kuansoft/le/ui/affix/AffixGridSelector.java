package com.kuansoft.le.ui.affix;

import com.kuansoft.le.affix.Affix;
import com.kuansoft.le.ui.common.GridSelector;
import com.kuansoft.le.ui.common.TextWithClassRenderer;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;

import java.util.Collection;

@CssImport("./themes/lastepochfiltergenerator/components/affix-grid.css")
public class AffixGridSelector extends GridSelector<Affix> {

    public AffixGridSelector() {
        addClassName("affix-grid");
    }

    @Override
    public void setItems(Collection<Affix> items) {
        super.setItems(items);
        Grid.Column<Affix> typeColumn = getContent().addColumn(new TextWithClassRenderer<>(Affix::getModifier, "type"))
                .setAutoWidth(true)
                .setFlexGrow(0);
        getContent().setColumnOrder(typeColumn, getNameColumn());
    }
}
