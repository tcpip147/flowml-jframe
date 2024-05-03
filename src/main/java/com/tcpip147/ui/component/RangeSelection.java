package com.tcpip147.ui.component;

import com.tcpip147.ui.FmlColor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class RangeSelection extends Shape {

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;

    @Override
    public void draw(Graphics2D g) {
        if (visible) {
            g.setColor(FmlColor.RANGE_DEFAULT);
            g.drawRect(x, y, width, height);
        }
    }
}
