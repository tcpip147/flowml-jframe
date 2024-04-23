package com.tcpip147.ui.component;

import lombok.Getter;
import lombok.ToString;

import java.awt.*;
import java.awt.event.MouseEvent;

@Getter
@ToString
public class Activity extends Shape {

    private String name;
    private int x;
    private int y;
    private int width;
    private int height = 26;

    public Activity(String name, int x, int y, int width) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.fillRect(x + 1, y + 1, width - 2, height - 2);
    }

    @Override
    public boolean isInBounded(MouseEvent e) {
        return e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height;
    }
}
