package com.tcpip147.ui;

import com.tcpip147.ui.component.*;
import com.tcpip147.ui.event.MouseContext;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Model {

    private List<Shape> shapeList = new LinkedList<>();
    private RangeSelection rangeSelection = new RangeSelection();

    private int getUniqueId() {
        int i = 0;
        boolean exists;
        do {
            exists = false;
            i++;
            for (Shape shape : shapeList) {
                if (shape.getId() == i) {
                    exists = true;
                }
            }
        } while (exists);
        return i;
    }

    public List<Shape> getShapeList() {
        return shapeList;
    }

    public void addShape(Shape shape) {
        shapeList.add(shape);
    }

    public Selectable getSelectableOnTop(MouseEvent e) {
        ListIterator<Shape> iterator = shapeList.listIterator(shapeList.size());
        while (iterator.hasPrevious()) {
            Shape shape = iterator.previous();
            if (shape instanceof Selectable) {
                Selectable selectable = (Selectable) shape;
                if (selectable.isInBoundedSelectable(e.getX(), e.getY())) {
                    return selectable;
                }
            }
        }
        return null;
    }

    public void unselectAllShape(MouseContext c) {
        if (!c.isControlDown) {
            for (Shape shape : shapeList) {
                if (shape instanceof Selectable) {
                    Selectable selectable = (Selectable) shape;
                    selectable.setSelected(false);
                }
            }
        }
    }

    public void selectShape(Selectable selectable) {
        ListIterator<Shape> iterator = shapeList.listIterator(shapeList.size());
        while (iterator.hasPrevious()) {
            Shape shape = iterator.previous();
            if (shape instanceof Selectable) {
                if (selectable == shape) {
                    iterator.remove();
                }
            }
        }
        selectable.setSelected(true);
        shapeList.add((Shape) selectable);
    }

    public RangeSelection getRangeSelection() {
        return rangeSelection;
    }

    public void showRangeSelection() {
        rangeSelection.setX(-1);
        rangeSelection.setY(-1);
        rangeSelection.setWidth(0);
        rangeSelection.setHeight(0);
        rangeSelection.setVisible(true);
    }

    public void resizeRangeSelection(MouseContext c, MouseEvent e) {
        if (e.getX() < c.originX) {
            rangeSelection.setX(e.getX());
            rangeSelection.setWidth(c.originX - e.getX());
        } else {
            rangeSelection.setX(c.originX);
            rangeSelection.setWidth(e.getX() - c.originX);
        }

        if (e.getY() < c.originY) {
            rangeSelection.setY(e.getY());
            rangeSelection.setHeight(c.originY - e.getY());
        } else {
            rangeSelection.setY(c.originY);
            rangeSelection.setHeight(e.getY() - c.originY);
        }
    }

    public void hideRangeSelection() {
        rangeSelection.setX(-1);
        rangeSelection.setY(-1);
        rangeSelection.setWidth(0);
        rangeSelection.setHeight(0);
        rangeSelection.setVisible(false);
    }

    public void selectInRangeSelection(MouseContext c) {
        unselectAllShape(c);
        for (Shape shape : shapeList) {
            if (shape instanceof Selectable) {
                Selectable selectable = (Selectable) shape;
                if (selectable.isInBoundedSelectable(rangeSelection)) {
                    selectable.setSelected(true);
                }
            }
        }
    }

    public void moveShapes(MouseContext c, MouseEvent e) {
        for (Shape shape : shapeList) {
            if (shape instanceof Selectable && shape instanceof Movable) {
                Selectable selectable = (Selectable) shape;
                if (selectable.isSelected()) {
                    Movable movable = (Movable) shape;
                    movable.setVisibleMovingGhost(true);
                    movable.move(c, e);
                    if (shape instanceof Activity) {
                        refreshDependentWires((Activity) shape);
                    }
                }
            }
        }
    }

    private void refreshDependentWires(Activity activity) {
        for (Shape shape : shapeList) {
            if (shape instanceof Wire) {
                Wire wire = (Wire) shape;
                if (wire.getSource() == activity || wire.getTarget() == activity) {
                    wire.refresh();
                }
            }
        }
    }

    public void adjustMovingShapes() {
        for (Shape shape : shapeList) {
            if (shape instanceof Selectable && shape instanceof Movable) {
                Selectable selectable = (Selectable) shape;
                if (selectable.isSelected()) {
                    Movable movable = (Movable) shape;
                    movable.adjustMovingResult();
                    if (shape instanceof Activity) {
                        refreshDependentWires((Activity) shape);
                    }
                }
            }
        }
    }

    public Resizable getResizablePosition(MouseEvent e) {
        ListIterator<Shape> iterator = shapeList.listIterator(shapeList.size());
        while (iterator.hasPrevious()) {
            Shape shape = iterator.previous();
            if (shape instanceof Resizable && shape instanceof Selectable) {
                Selectable selectable = (Selectable) shape;
                if (selectable.isSelected()) {
                    Resizable resizable = (Resizable) shape;
                    if (resizable.isInBoundedResizable(e.getX(), e.getY())) {
                        return resizable;
                    }
                }
            }
        }
        return null;
    }

    public void resizeShape(MouseContext c, MouseEvent e) {
        Resizable resizable = c.resizable;
        resizable.setVisibleResizingGhost(true);
        resizable.resize(c, e);
    }

    public void adjustResizeShape(MouseContext c) {
        Resizable resizable = c.resizable;
        resizable.adjustResizeResult();
    }

    public Activity createActivity(int id, String name, int x, int y, int width) {
        if (id == 0) {
            id = getUniqueId();
        }
        Activity activity = new Activity(id, name, x, y, width);
        addShape(activity);
        return activity;
    }

    public Wire createWire(Activity source, String sourceDir, int sourceX, Activity target, String targetDir, int targetX) {
        Wire wire = new Wire(source, sourceDir, sourceX, target, targetDir, targetX);
        addShape(wire);
        return wire;
    }

    public Linkable getLinkablePosition(MouseEvent e) {
        ListIterator<Shape> iterator = shapeList.listIterator(shapeList.size());
        while (iterator.hasPrevious()) {
            Shape shape = iterator.previous();
            if (shape instanceof Linkable && shape instanceof Selectable) {
                Selectable selectable = (Selectable) shape;
                if (selectable.isSelected()) {
                    Linkable linkable = (Linkable) shape;
                    if (linkable.isInBoundedLinkable(e.getX(), e.getY())) {
                        return linkable;
                    }
                }
            }
        }
        return null;
    }
}
