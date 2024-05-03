package com.tcpip147.ui.component;

import com.tcpip147.ui.event.MouseContext;

import java.awt.event.MouseEvent;

public interface Linkable {

    boolean isInBoundedLinkable(int x, int y);

    int getLinkingPosition(int px, int py);

    LinkingPort getSource();

    LinkingPort getTarget();

    void setLinkingPoint(MouseContext c, MouseEvent e, LinkingPortPoint point);

    void linkStart(MouseContext c, MouseEvent e);

    void adjustLinkingResult();
}
