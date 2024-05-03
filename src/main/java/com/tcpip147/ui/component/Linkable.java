package com.tcpip147.ui.component;

public interface Linkable {

    boolean isInBoundedLinkable(int x, int y);

    int getLinkingPosition(int px, int py);
}
