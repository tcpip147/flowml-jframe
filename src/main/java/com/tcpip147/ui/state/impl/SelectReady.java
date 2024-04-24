package com.tcpip147.ui.state.impl;

import com.tcpip147.ui.component.Activity;
import com.tcpip147.ui.component.Shape;
import com.tcpip147.ui.state.ActionFilter;
import com.tcpip147.ui.state.State;
import com.tcpip147.ui.state.EventHandler;

import java.awt.event.MouseEvent;

public class SelectReady extends EventHandler {

    public SelectReady() {
        super(State.SELECT_READY, ActionFilter.LEFT);
    }

    @Override
    protected void onMousePressed(MouseEvent e) {
        Shape shape = model.getTopShape(e);
        if (shape == null) {
            //
        } else if (shape instanceof Activity && shape.isSelected()) {
            changeState(State.DRAG_READY);
        } else {
            model.selectShape(shape);
            changeStateAndRepaint(State.DRAG_READY);
        }
    }

    @Override
    protected void onMouseReleased(MouseEvent e) {

    }
}
