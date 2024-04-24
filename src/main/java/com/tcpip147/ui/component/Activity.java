package com.tcpip147.ui.component;

import com.tcpip147.ui.MyColor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseEvent;

@Getter
@Setter
public class Activity extends Shape {

    public static final int HEIGHT = 26;

    private String name;
    private int x;
    private int y;
    private int width;
    private int height = HEIGHT;

    public Activity(String name, int x, int y, int width) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(MyColor.ACTIVITY_OUTLINE);
        g.fillRect(x, y, width, height);
        g.setColor(MyColor.ACTIVITY_DEFAULT);
        g.fillRect(x + 1, y + 1, width - 2, height - 2);
        if (isSelected()) {
            g.setColor(MyColor.DOT_OUTLINE);
            g.fillRect(x - 2, y + height / 2 - 3, 6, 6);
            g.fillRect(x + width - 4, y + height / 2 - 3, 6, 6);
            g.setColor(MyColor.DOT_DEFAULT);
            g.fillRect(x - 1, y + height / 2 - 2, 4, 4);
            g.fillRect(x + width - 3, y + height / 2 - 2, 4, 4);
        }
    }

    @Override
    public boolean isInBounded(MouseEvent e) {
        return e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height;
    }
}
