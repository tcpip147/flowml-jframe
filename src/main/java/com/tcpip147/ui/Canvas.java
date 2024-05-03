package com.tcpip147.ui;

import com.tcpip147.ui.component.Shape;
import com.tcpip147.ui.component.*;
import com.tcpip147.ui.event.MouseContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Canvas extends JPanel {

    public static final int GRID_SIZE = 30;

    private Model model = new Model();
    private State state = State.SELECT_READY;
    private MouseContext c = new MouseContext();

    public Canvas() {
        setLayout(null);
        setBackground(new Color(60, 63, 65));
        setFocusable(true);

        Activity activity = model.createActivity(1, "안녕", 90, 120, 120);
        Activity activity2 = model.createActivity(2, "안녕", 90, 210, 120);
        Wire wire = model.createWire(activity, "N", 30, activity2, "S", 45);
        wire.setTransition("find");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                c.setUp(e);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (state == State.SELECT_READY) {
                        Resizable resizable = model.getResizablePosition(e);
                        Linkable linkable = model.getLinkablePosition(e);
                        if (resizable != null) {
                            c.resizable = resizable;
                            c.resizePosition = resizable.getResizingPosition(e.getX(), e.getY());
                            resizable.resizeStart(c, e);
                            state = State.RESIZE_STARTED;
                        } else if (linkable != null) {
                            c.linkable = linkable;
                            c.linkPosition = linkable.getLinkingPosition(e.getX(), e.getY());
                            linkable.linkStart(c, e);
                            state = State.LINK_STARTED;
                        } else {
                            Selectable selectable = model.getSelectableOnTop(e);
                            if (selectable == null) {
                                model.showRangeSelection();
                                state = State.RANGE_MODE;
                            } else {
                                c.selectable = selectable;
                                if (!selectable.isSelected()) {
                                    model.unselectAllShape(c);
                                    model.selectShape(c.selectable);
                                }
                                if (selectable instanceof Movable) {
                                    state = State.MOVE_READY;
                                }
                            }
                        }
                        repaint();
                    }
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (state == State.MOVE_READY) {
                    state = State.MOVE_STARTED;
                } else if (state == State.MOVE_STARTED) {
                    model.moveShapes(c, e);
                    c.prevX = e.getX();
                    c.prevY = e.getY();
                    repaint();
                } else if (state == State.RANGE_MODE) {
                    setCursor(FmlCursor.CROSS_HAIR);
                    model.resizeRangeSelection(c, e);
                    repaint();
                } else if (state == State.RESIZE_STARTED) {
                    model.resizeShape(c, e);
                    repaint();
                } else if (state == State.LINK_STARTED) {
                    model.linkWire(c, e);
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == State.SELECT_READY) {
                    Resizable resizable = model.getResizablePosition(e);
                    if (resizable != null) {
                        setCursor(FmlCursor.RESIZE_HORIZONTAL);
                    } else {
                        setCursor(FmlCursor.DEFAULT);
                    }
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (state == State.MOVE_READY || state == State.MOVE_STARTED) {
                    if (state == State.MOVE_READY) {
                        model.unselectAllShape(c);
                        model.selectShape(c.selectable);
                    } else {
                        model.adjustMovingShapes();
                    }
                    state = State.SELECT_READY;
                    repaint();
                } else if (state == State.RANGE_MODE) {
                    setCursor(FmlCursor.DEFAULT);
                    model.selectInRangeSelection(c);
                    model.hideRangeSelection();
                    state = State.SELECT_READY;
                    repaint();
                } else if (state == State.RESIZE_STARTED) {
                    model.adjustResizeShape(c);
                    state = State.SELECT_READY;
                    repaint();
                } else if (state == State.LINK_STARTED) {
                    model.adjustLinkingWire(c);
                    state = State.SELECT_READY;
                    repaint();
                }
                c.reset();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 17) { // Ctrl
                    c.isControlDown = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 17) { // Ctrl
                    c.isControlDown = false;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape shape : model.getShapeList()) {
            shape.draw(g);
        }
        RangeSelection rangeSelection = model.getRangeSelection();
        rangeSelection.draw(g);
    }
}
