package com.tcpip147.ui.component;

import com.tcpip147.ui.event.MouseContext;

import java.awt.event.MouseEvent;

public interface Resizable {

    boolean isInBoundedResizable(int px, int py);

    int getResizingPosition(int px, int py);

    void resizeStart(MouseContext c, MouseEvent e);

    void resize(MouseContext c, MouseEvent e);

    void setVisibleResizingGhost(boolean visible);

    void adjustResizeResult();
}
