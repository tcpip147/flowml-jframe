package com.tcpip147.ui.event;

import com.tcpip147.ui.component.Linkable;
import com.tcpip147.ui.component.Resizable;
import com.tcpip147.ui.component.Selectable;

import java.awt.event.MouseEvent;

public class MouseContext {

    public int originX;
    public int originY;
    public int prevX;
    public int prevY;
    public boolean isControlDown;
    public Selectable selectable;
    public Resizable resizable;
    public int resizePosition;
    public Linkable linkable;
    public int linkPosition;

    public void reset() {
        originX = 0;
        originY = 0;
        prevX = 0;
        prevY = 0;
        isControlDown = false;
        selectable = null;
        resizable = null;
        resizePosition = 0;
        linkable = null;
        linkPosition = 0;
    }

    public void setUp(MouseEvent e) {
        originX = e.getX();
        originY = e.getY();
        prevX = e.getX();
        prevY = e.getY();
    }
}
