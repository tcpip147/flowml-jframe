package com.tcpip147.ui.component;

import com.tcpip147.ui.Canvas;
import com.tcpip147.ui.FmlColor;
import com.tcpip147.ui.event.MouseContext;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class Activity extends Shape implements Selectable, Movable, Resizable {

    private int id;
    private String name;
    private int x;
    private int y;
    private int width;
    private int height = 26;
    private boolean selected;
    private boolean visibleMovingGhost;
    private boolean visibleResizingGhost;
    private int ghostX;
    private int ghostWidth;

    public Activity(int id, String name, int x, int y, int width) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
    }

    @Override
    public void draw(Graphics2D g) {
        drawMovingGhost(g);
        drawShape(g);
        drawResizingGhost(g);
        drawSelectionMark(g);
    }

    private void drawMovingGhost(Graphics2D g) {
        if (visibleMovingGhost) {
            g.setColor(FmlColor.GHOST_DEFAULT);
            g.drawRoundRect(getXofMovingGhost(), getYofMovingGhost(), width, height, 5, 5);
        }
    }

    private void drawResizingGhost(Graphics2D g) {
        if (visibleResizingGhost) {
            g.setColor(FmlColor.GHOST_DEFAULT);
            g.drawRoundRect(getXofResizingGhost(), getYofResizingGhost(), getWidthOfResizingGhost(), getHeightOfResizingGhost(), 5, 5);
        }
    }

    private void drawShape(Graphics2D g) {
        g.setColor(FmlColor.ACTIVITY_OUTLINE);
        g.fillRoundRect(x - 1, y - 1, width + 2, height + 2, 5, 5);
        g.setColor(FmlColor.ACTIVITY_DEFAULT);
        g.fillRoundRect(x, y, width, height, 5, 5);
        g.setColor(FmlColor.ACTIVITY_NAME);
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(name, x + (width - metrics.stringWidth(name)) / 2, y + (height - metrics.getHeight()) / 2 + metrics.getAscent());
    }

    private void drawSelectionMark(Graphics2D g) {
        if (selected) {
            g.setColor(FmlColor.ACTIVITY_SELECTION_MARK_OUTER);
            g.fillRect(x - 3, y + height / 2 - 3, 6, 6);
            g.fillRect(x + width - 3, y + height / 2 - 3, 6, 6);
            g.setColor(FmlColor.ACTIVITY_SELECTION_MARK);
            g.fillRect(x - 2, y + height / 2 - 2, 4, 4);
            g.fillRect(x + width - 2, y + height / 2 - 2, 4, 4);
        }
    }

    @Override
    public boolean isInBoundedSelectable(int px, int py) {
        return px > x && px < x + width && py > y && py < y + height;
    }

    @Override
    public boolean isInBoundedSelectable(RangeSelection rangeSelection) {
        if ((x > rangeSelection.getX() && x + width < rangeSelection.getX() + rangeSelection.getWidth()) &&
                (y > rangeSelection.getY() && y + height < rangeSelection.getY() + rangeSelection.getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void move(MouseContext c, MouseEvent e) {
        setX(x + e.getX() - c.prevX);
        setY(y + e.getY() - c.prevY);
    }

    @Override
    public void setVisibleMovingGhost(boolean visible) {
        this.visibleMovingGhost = visible;
    }

    @Override
    public void adjustMovingResult() {
        this.x = getXofMovingGhost();
        this.y = getYofMovingGhost();
        visibleMovingGhost = false;
    }

    public int getXofMovingGhost() {
        return (int) Math.round(x / (double) Canvas.GRID_SIZE) * Canvas.GRID_SIZE;
    }

    public int getYofMovingGhost() {
        return (int) Math.round(y / (double) Canvas.GRID_SIZE) * Canvas.GRID_SIZE;
    }

    @Override
    public boolean isInBoundedResizable(int px, int py) {
        return getResizingPosition(px, py) > 0;
    }

    @Override
    public int getResizingPosition(int px, int py) {
        if (x - 10 < px && x + 10 > px && y < py && y + height > py) {
            return 1;
        } else if (x + width - 10 < px && x + width + 10 > px && y < py && y + height > py) {
            return 2;
        }
        return 0;
    }

    @Override
    public void resizeStart(MouseContext c, MouseEvent e) {
        ghostX = x;
        ghostWidth = width;
    }

    @Override
    public void resize(MouseContext c, MouseEvent e) {
        if (c.resizePosition == 1) {
            int w = (int) Math.round((x + width - e.getX()) / (double) Canvas.GRID_SIZE) * Canvas.GRID_SIZE;
            if (w > Canvas.GRID_SIZE * 3) {
                ghostX = (int) Math.round(e.getX() / (double) Canvas.GRID_SIZE) * Canvas.GRID_SIZE;
                ghostWidth = x + width - ghostX;
            }
        } else if (c.resizePosition == 2) {
            int w = (int) Math.round((e.getX() - x) / (double) Canvas.GRID_SIZE) * Canvas.GRID_SIZE;
            if (w > Canvas.GRID_SIZE * 3) {
                ghostWidth = w;
            }
        }
    }

    @Override
    public void setVisibleResizingGhost(boolean visible) {
        this.visibleResizingGhost = visible;
    }

    @Override
    public void adjustResizeResult() {
        x = ghostX;
        width = ghostWidth;
        visibleResizingGhost = false;
    }

    public int getXofResizingGhost() {
        return ghostX;
    }

    public int getYofResizingGhost() {
        return y;
    }

    public int getWidthOfResizingGhost() {
        return ghostWidth;
    }

    public int getHeightOfResizingGhost() {
        return height;
    }
}
