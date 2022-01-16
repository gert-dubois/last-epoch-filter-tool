package com.kuansoft.le.ui.common;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Set;
import java.util.function.Consumer;

public class PanelSelector<T> extends Composite<HorizontalLayout> implements Selector<T> {
    protected final Selector<T> selector;
    protected final Checkbox selectAll;
    protected final Details details;
    protected final String summary;

    public <C extends Component & Selector<T>> PanelSelector(String summary, C selector) {
        this.selector = selector;
        selectAll = new Checkbox();
        selectAll.getStyle().set("margin-top", "var(--lumo-space-s)");
        selectAll.addValueChangeListener(event -> {
            if (event.getValue()) {
                selectAll();
            } else {
                deselectAll();
            }
        });
        this.summary = summary;
        details = new Details(createSummaryHeader(), selector);
        if(selector instanceof GridSelector<?> gridSelector) {
            details.addOpenedChangeListener(event -> {
                if(event.isOpened()) {
                    gridSelector.getContent().recalculateColumnWidths();
                }
             });
        }
        getContent().setPadding(false);
        getContent().add(selectAll);
        getContent().addAndExpand(details);
    }

    protected String createSummaryHeader() {
        return this.summary + " (" + getSelectedItems().size() + "/" + getItems().size() + ")";
    }

    @Override
    public Set<T> getSelectedItems() {
        return selector.getSelectedItems();
    }

    @Override
    public Set<T> getItems() {
        return selector.getItems();
    }

    @Override
    public boolean hasItem(T item) {
        return selector.hasItem(item);
    }

    @Override
    public boolean areAllItemsSelected() {
        return selector.areAllItemsSelected();
    }

    @Override
    public void select(T item) {
        if(selector.hasItem(item)) {
            selector.select(item);
            updatePanel();
        }
    }

    @Override
    public void deselect(T item) {
        if(selector.hasItem(item)) {
            selector.deselect(item);
            updatePanel();
        }
    }

    @Override
    public void selectAll() {
        selector.selectAll();
        updatePanel();
    }

    @Override
    public void deselectAll() {
        selector.deselectAll();
        updatePanel();
    }

    private void updatePanel() {
        details.setSummaryText(createSummaryHeader());
        if (areAllItemsSelected()) {
            selectAll.setIndeterminate(false);
            selectAll.setValue(true);
        } else if (getSelectedItems().size() > 0) {
            selectAll.setIndeterminate(true);
        } else {
            selectAll.setIndeterminate(false);
            selectAll.setValue(false);
        }
    }

    @Override
    public void onSelectionChanged(Consumer<T> onItemSelected, Consumer<T> onItemDeselected) {
        this.selector.onSelectionChanged(onItemSelected, onItemDeselected);
    }
}
