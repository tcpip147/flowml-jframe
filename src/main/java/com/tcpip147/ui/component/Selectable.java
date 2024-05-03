package com.tcpip147.ui.component;

public interface Selectable {

    boolean isInBoundedSelectable(int px, int py);

    boolean isInBoundedSelectable(RangeSelection rangeSelection);

    boolean isSelected();

    void setSelected(boolean selected);
}
