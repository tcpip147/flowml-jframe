package com.tcpip147.ui.component;

import com.tcpip147.path.PathFinder;
import com.tcpip147.ui.FmlColor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Wire extends Shape implements Selectable, Linkable {

    private Activity source;
    private String sourceDir;
    private int sourceX;
    private Activity target;
    private String targetDir;
    private int targetX;
    private String transition;
    private List<Point> points = new ArrayList<>();

    private boolean selected;

    public Wire(Activity source, String sourceDir, int sourceX, Activity target, String targetDir, int targetX) {
        this.source = source;
        this.sourceDir = sourceDir;
        this.sourceX = sourceX;
        this.target = target;
        this.targetDir = targetDir;
        this.targetX = targetX;
        refresh();
    }

    public void refresh() {
        if (source != null && target != null) {
            Rectangle sourceRect = new Rectangle(source.getX(), source.getY(), source.getWidth(), source.getHeight());
            Rectangle targetRect = new Rectangle(target.getX(), target.getY(), target.getWidth(), target.getHeight());
            PathFinder pathFinder = new PathFinder(sourceRect, sourceDir, source.getX() + sourceX, targetRect, targetDir, target.getX() + targetX);
            points = pathFinder.find();
            if (points.size() > 1) {
                digestPoints();
                modifyLine();
                setOutlinePoint(points.get(0), sourceDir, sourceX, source);
                setOutlinePoint(points.get(points.size() - 1), targetDir, targetX, target);
            }
        }
    }

    protected void digestPoints() {
        List<Point> digested = new ArrayList<>();
        digested.add(points.get(0));
        if (points.size() > 1) {
            digested.add(points.get(1));
        }
        for (int i = 2; i < points.size(); i++) {
            Point pPrev = points.get(i - 2);
            Point prev = points.get(i - 1);
            Point current = points.get(i);
            boolean noMoveX = pPrev.x == prev.x && pPrev.x == current.x;
            boolean noMoveY = pPrev.y == prev.y && pPrev.y == current.y;
            if (!noMoveX && !noMoveY) {
                digested.add(new Point(current));
            } else {
                digested.set(digested.size() - 1, current);
            }
        }
        points = digested;
    }

    protected void modifyLine() {
        Point center = new Point(((source.getX() + source.getWidth() / 2) + (target.getX() + target.getWidth() / 2)) / 2, ((source.getY() + source.getHeight() / 2) + (target.getY() + target.getHeight() / 2)) / 2);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            int minX = Math.min(Math.min(p1.x, p2.x), center.x);
            int maxX = Math.max(Math.max(p1.x, p2.x), center.x);
            int minY = Math.min(Math.min(p1.y, p2.y), center.y);
            int maxY = Math.max(Math.max(p1.y, p2.y), center.y);
            Rectangle rect = new Rectangle(minX, minY, maxX - minX, maxY - minY);
            if (!(isHitX(source, rect) && isHitY(source, rect)) && !(isHitX(target, rect) && isHitY(target, rect))) {
                if (p1.y == p2.y) {
                    p1.y = center.y;
                    p2.y = center.y;
                } else {
                    p1.x = center.x;
                    p2.x = center.x;
                }
            }
        }
    }

    private boolean isHitX(Activity activity, Rectangle rect) {
        int min = activity.getX() < rect.x ? activity.getX() : rect.x;
        int max = activity.getX() + activity.getWidth() > rect.x + rect.width ? activity.getX() + activity.getWidth() : rect.x + rect.width;
        if (max - min - activity.getWidth() - rect.width <= 0) {
            return true;
        }
        return false;
    }

    private boolean isHitY(Activity activity, Rectangle rect) {
        int min = activity.getY() < rect.y ? activity.getY() : rect.y;
        int max = activity.getY() + activity.getHeight() > rect.y + rect.height ? activity.getY() + activity.getHeight() : rect.y + rect.height;
        if (max - min - activity.getHeight() - rect.height <= 0) {
            return true;
        }
        return false;
    }

    protected void setOutlinePoint(Point point, String direction, int offset, Activity activity) {
        if ("N".equals(direction)) {
            point.x = activity.getX() + offset;
            point.y = activity.getY();
        } else if ("E".equals(direction)) {
            point.x = activity.getX() + activity.getWidth();
            point.y = activity.getY() + activity.getHeight() / 2;
        } else if ("S".equals(direction)) {
            point.x = activity.getX() + offset;
            point.y = activity.getY() + activity.getHeight();
        } else if ("W".equals(direction)) {
            point.x = activity.getX();
            point.y = activity.getY() + activity.getHeight() / 2;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        drawLines(g);
        drawTransition(g);
    }

    private void drawLines(Graphics2D g) {
        for (int i = 1; i < points.size(); i++) {
            Point current = points.get(i);
            Point prev = points.get(i - 1);
            if (selected) {
                g.setColor(FmlColor.WIRE_SELECTED);
            } else {
                g.setColor(FmlColor.WIRE_DEFAULT);
            }
            g.drawLine(current.x, current.y, prev.x, prev.y);
            if (i == points.size() - 1) {
                if ("N".equals(targetDir)) {
                    g.fillPolygon(new int[]{current.x - 5, current.x, current.x + 5}, new int[]{current.y - 7, current.y, current.y - 7}, 3);
                } else if ("E".equals(targetDir)) {
                    g.fillPolygon(new int[]{current.x + 7, current.x, current.x + 7}, new int[]{current.y - 5, current.y, current.y + 5}, 3);
                } else if ("S".equals(targetDir)) {
                    g.fillPolygon(new int[]{current.x - 5, current.x, current.x + 5}, new int[]{current.y + 7, current.y, current.y + 7}, 3);
                } else {
                    g.fillPolygon(new int[]{current.x - 7, current.x, current.x - 7}, new int[]{current.y - 5, current.y, current.y + 5}, 3);
                }
            }
        }

        if (selected) {
            if (points.size() > 1) {
                Point start = points.get(0);
                Point end = points.get(points.size() - 1);
                g.setColor(FmlColor.WIRE_SELECTION_MARK_OUTER);
                g.fillRect(start.x - 3, start.y - 3, 6, 6);
                g.fillRect(end.x - 3, end.y - 3, 6, 6);
                g.setColor(FmlColor.WIRE_SELECTION_MARK);
                g.fillRect(start.x - 2, start.y - 2, 4, 4);
                g.fillRect(end.x - 2, end.y - 2, 4, 4);
            }
        }
    }

    private void drawTransition(Graphics2D g) {
        if (transition != null) {
            if (points.size() > 1) {
                Point current = points.get(points.size() / 2);
                Point prev = points.get(points.size() / 2 - 1);
                FontMetrics metrics = g.getFontMetrics();
                int x = (prev.x + current.x - metrics.stringWidth(transition)) / 2;
                int y = (prev.y + current.y - metrics.getHeight()) / 2 + metrics.getAscent();
                g.setColor(FmlColor.DEFAULT);
                g.fillRect(x - 5, (prev.y + current.y - metrics.getHeight()) / 2, metrics.stringWidth(transition) + 10, metrics.getHeight());
                if (selected) {
                    g.setColor(FmlColor.WIRE_SELECTED);
                } else {
                    g.setColor(FmlColor.WIRE_DEFAULT);
                }
                g.drawString(transition, x, y);
            }
        }
    }

    @Override
    public boolean isInBoundedSelectable(int px, int py) {
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            int range = 10;
            if (p1.y == p2.y) {
                if (p1.x < p2.x) {
                    if (px > p1.x && px < p2.x && py > p1.y - range && py < p1.y + range) {
                        return true;
                    }
                } else {
                    if (px > p2.x && px < p1.x && py > p2.y - range && py < p2.y + range) {
                        return true;
                    }
                }
            } else {
                if (p1.y < p2.y) {
                    if (py > p1.y && py < p2.y && px > p1.x - range && px < p1.x + range) {
                        return true;
                    }
                } else {
                    if (py > p2.y && py < p1.y && px > p2.x - range && px < p2.x + range) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isInBoundedSelectable(RangeSelection rangeSelection) {
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
    public boolean isInBoundedLinkable(int x, int y) {
        return getLinkingPosition(x, y) > 0;
    }

    @Override
    public int getLinkingPosition(int px, int py) {
        if (points.size() > 1) {
            Point p1 = points.get(0);
            Point p2 = points.get(points.size() - 1);
            if (p1.x - 10 < px && p1.x + 10 > px && p1.y - 10 < py && p1.y + 10 > py) {
                return 1;
            } else if (p2.x - 10 < px && p2.x + 10 > px && p2.y - 10 < py && p2.y + 10 > py) {
                return 2;
            }
        }
        return 0;
    }
}
