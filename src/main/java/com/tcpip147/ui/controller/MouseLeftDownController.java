package com.tcpip147.ui.controller;

import com.tcpip147.ui.ActionManager;
import com.tcpip147.ui.ActionState;
import com.tcpip147.ui.Canvas;
import com.tcpip147.ui.component.Shape;
import com.tcpip147.ui.model.ShapeModel;
import com.tcpip147.ui.model.ShapeReadService;
import com.tcpip147.ui.model.ShapeUpdateService;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseLeftDownController extends MouseAdapter {

    private Canvas canvas;
    private ActionManager actionManager;
    private ShapeReadService readService;
    private ShapeUpdateService updateService;

    public MouseLeftDownController(Canvas canvas, ActionManager actionManager, ShapeModel model) {
        this.canvas = canvas;
        this.actionManager = actionManager;
        this.readService = model.getReadService();
        this.updateService = model.getUpdateService();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (actionManager.getState() == ActionState.SELECT_READY) {
                Shape shape = readService.getTopShape(e);
                shape.setSelected(true);
                updateService.putShapeOnTop(shape);
                canvas.repaint();
            }
        }
    }
}
