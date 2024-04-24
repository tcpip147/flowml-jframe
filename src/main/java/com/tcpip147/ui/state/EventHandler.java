package com.tcpip147.ui.state;

import com.tcpip147.ui.Canvas;
import com.tcpip147.ui.ShapeModel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@Getter
@Setter
public abstract class EventHandler {

    private final State requiredState;
    private final ActionFilter actionFilter;
    private State currentState;
    private StateManager stateManager;
    protected ShapeModel model;
    private Canvas canvas;

    public EventHandler(State requiredState, ActionFilter actionFilter) {
        this.requiredState = requiredState;
        this.actionFilter = actionFilter;
    }

    protected void changeState(State state) {
        stateManager.setCurrentState(state);
    }

    protected void changeStateAndRepaint(State state) {
        stateManager.setCurrentState(state);
        canvas.repaint();
    }

    protected void repaint() {
        canvas.repaint();
    }

    public MouseListener getMousePressedListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean pass = false;
                if (actionFilter == ActionFilter.LEFT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isLeftMouseButton(e);
                }
                if (actionFilter == ActionFilter.RIGHT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isRightMouseButton(e);
                }

                if (pass && requiredState == currentState) {
                    onMousePressed(e);
                }
            }
        };
    }

    protected void onMousePressed(MouseEvent e) {

    }

    public MouseListener getMouseReleasedListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                boolean pass = false;
                if (actionFilter == ActionFilter.LEFT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isLeftMouseButton(e);
                }
                if (actionFilter == ActionFilter.RIGHT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isRightMouseButton(e);
                }

                if (pass && requiredState == currentState) {
                    onMouseReleased(e);
                }
            }
        };
    }

    protected void onMouseReleased(MouseEvent e) {

    }

    public MouseMotionListener getMouseDraggedListener() {
        return new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                boolean pass = false;
                if (actionFilter == ActionFilter.LEFT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isLeftMouseButton(e);
                }
                if (actionFilter == ActionFilter.RIGHT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isRightMouseButton(e);
                }

                if (pass && requiredState == currentState) {
                    onMouseDragged(e);
                }
            }
        };
    }

    protected void onMouseDragged(MouseEvent e) {

    }

    public MouseMotionListener getMouseMovedListener() {
        return new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean pass = false;
                if (actionFilter == ActionFilter.LEFT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isLeftMouseButton(e);
                }
                if (actionFilter == ActionFilter.RIGHT || actionFilter == ActionFilter.BOTH) {
                    pass = SwingUtilities.isRightMouseButton(e);
                }

                if (pass && requiredState == currentState) {
                    onMouseMoved(e);
                }
            }
        };
    }

    protected void onMouseMoved(MouseEvent e) {

    }
}
