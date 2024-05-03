package com.tcpip147.ui.component;

import com.tcpip147.ui.event.MouseContext;

import java.awt.event.MouseEvent;

public interface Movable {

    void move(MouseContext c, MouseEvent e);

    void setVisibleMovingGhost(boolean visible);

    void adjustMovingResult();
}
