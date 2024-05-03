package com.tcpip147.ui.component;

public interface LinkingPort {

    void setVisibleLinkingMarks(boolean visible);

    boolean isInBoundedLinkingPort(int px, int py);

    LinkingPortPoint getLinkingPortPoint(int px, int py);
}
