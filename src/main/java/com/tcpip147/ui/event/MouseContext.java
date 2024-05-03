package com.tcpip147.ui.event;

import com.tcpip147.ui.component.Movable;
import com.tcpip147.ui.component.Resizable;
import com.tcpip147.ui.component.Selectable;
import com.tcpip147.ui.component.Shape;

import java.awt.event.MouseEvent;

public class MouseContext {

    public int originX;
    public int originY;
    public int prevX;
    public int prevY;
    public boolean isControlDown;
    public int wireMovePosition;
    public Selectable selectable;
    public Resizable resizable;
    public int resizePosition;
    public int temp;

    public void reset() {
        originX = 0;
        originY = 0;
        prevX = 0;
        prevY = 0;
        isControlDown = false;
        resizePosition = 0;
        selectable = null;
        wireMovePosition = 0;
    }

    public void setUp(MouseEvent e) {
        originX = e.getX();
        originY = e.getY();
        prevX = e.getX();
        prevY = e.getY();
    }
}
