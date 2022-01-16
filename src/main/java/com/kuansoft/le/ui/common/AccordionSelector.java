package com.kuansoft.le.ui.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasOrderedComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class AccordionSelector<T> extends Composite<VerticalLayout> implements Selector<T>, HasSize, HasOrderedComponents {

    private final List<Selector<T>> selectors;

    public AccordionSelector() {
        this.selectors = new ArrayList<>();
        getContent().setPadding(false);
        getContent().setSpacing(false);
    }

    public <C extends Component & Selector<T>> void add(C selector) {
        getContent().add(selector);
        this.selectors.add(selector);
        selector.onSelectionChanged(this::select, this::deselect);
    }

    @Override
    public Set<T> getSelectedItems() {
        return this.selectors.stream()
                .map(Selector::getSelectedItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<T> getItems() {
        return selectors.stream()
                .map(Selector::getItems)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean hasItem(T item) {
        return selectors.stream()
                .anyMatch(selector -> selector.hasItem(item));
    }

    @Override
    public boolean areAllItemsSelected() {
        for (Selector<T> selector : selectors) {
            if(!selector.areAllItemsSelected()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void select(T item) {
        for (Selector<T> selector : this.selectors) {
            if(selector.hasItem(item)) {
                selector.select(item);
            }
        }
    }

    @Override
    public void deselect(T item) {
        for (Selector<T> selector : selectors) {
            if(selector.hasItem(item)) {
                selector.deselect(item);
            }
        }
    }

    @Override
    public void selectAll() {
        for (Selector<T> selector : selectors) {
            selector.selectAll();
        }
    }

    @Override
    public void deselectAll() {
        for (Selector<T> selector : selectors) {
            selector.deselectAll();
        }
    }

    @Override
    public void onSelectionChanged(Consumer<T> onItemSelected, Consumer<T> onItemDeselected) {
        for (Selector<T> selector : selectors) {
            selector.onSelectionChanged(onItemSelected, onItemDeselected);
        }
    }

    public void clear() {
        this.selectors.clear();
        getContent().removeAll();
    }
}
