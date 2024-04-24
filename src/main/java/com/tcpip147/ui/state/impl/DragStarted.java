package com.tcpip147.ui.state.impl;

import com.tcpip147.ui.state.ActionFilter;
import com.tcpip147.ui.state.EventHandler;
import com.tcpip147.ui.state.State;

import java.awt.event.MouseEvent;

public class DragStarted extends EventHandler {

    public DragStarted() {
        super(State.DRAG_STARTED, ActionFilter.LEFT);
    }

    @Override
    protected void onMousePressed(MouseEvent e) {

    }

    @Override
    protected void onMouseReleased(MouseEvent e) {

    }
}
