package com.kuansoft.le.ui.common;

import com.kuansoft.le.game.HasDisplayName;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

@CssImport("./themes/lastepochfiltergenerator/components/selector-grid.css")
public abstract class GridSelector<T extends HasDisplayName> extends Composite<Grid<T>> implements Selector<T>, HasStyle {

    protected GridMultiSelectionModel<T> selectionModel;
    private Set<T> items;
    private Grid.Column<T> nameColumn;

    public GridSelector() {
        addClassName("selector-grid");
        selectionModel = (GridMultiSelectionModel<T>) getContent().setSelectionMode(Grid.SelectionMode.MULTI);
        getContent().addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_COMPACT,
                GridVariant.LUMO_WRAP_CELL_CONTENT,
                GridVariant.LUMO_ROW_STRIPES);
        getContent().setAllRowsVisible(true);
        selectionModel.setSelectAllCheckboxVisibility(GridMultiSelectionModel.SelectAllCheckboxVisibility.HIDDEN);
        getContent().setWidthFull();
    }

    @Override
    public Set<T> getSelectedItems() {
        return selectionModel.getSelectedItems();
    }

    public void setItems(Collection<T> items) {
        nameColumn = addDisplayNameColumn(items);
    }

    private Grid.Column<T> addDisplayNameColumn(Collection<T> items) {
        this.items = Set.copyOf(items);
        Grid.Column<T> displayNameColumn = getContent().addColumn(HasDisplayName::getDisplayName)
                .setFlexGrow(20);
        getContent().setItems(this.items);
        getContent().sort(GridSortOrder.asc(displayNameColumn).build());
        return displayNameColumn;
    }

    public Grid.Column<T> getNameColumn() {
        return nameColumn;
    }

    @Override
    public Set<T> getItems() {
        return items;
    }

    @Override
    public boolean hasItem(T item) {
        return items.contains(item);
    }

    @Override
    public boolean areAllItemsSelected() {
        return selectionModel.getSelectedItems().size() == items.size();
    }

    public void select(T item) {
        if (hasItem(item)) {
            selectionModel.select(item);
        }
    }

    @Override
    public void deselect(T item) {
        if (hasItem(item)) {
            selectionModel.deselect(item);
        }
    }

    @Override
    public void selectAll() {
        selectionModel.selectAll();
    }

    @Override
    public void deselectAll() {
        selectionModel.deselectAll();
    }

    @Override
    public void onSelectionChanged(Consumer<T> onItemSelected, Consumer<T> onItemDeselected) {
        selectionModel.addMultiSelectionListener(event -> {
            for (T affix : event.getRemovedSelection()) {
                onItemDeselected.accept(affix);
            }
            for (T affix : event.getAddedSelection()) {
                onItemSelected.accept(affix);
            }
        });
    }
}
