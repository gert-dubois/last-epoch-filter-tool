package com.kuansoft.le.ui.common;

import java.util.Set;
import java.util.function.Consumer;

public interface Selector<T> {

    Set<T> getSelectedItems();

    Set<T> getItems();

    boolean hasItem(T item);

    boolean areAllItemsSelected();

    void select(T item);

    void deselect(T item);

    void selectAll();

    void deselectAll();

    void onSelectionChanged(Consumer<T> onItemSelected, Consumer<T> onItemDeselected);
}
