package com.fei.paintUI.shape;


import java.awt.Color;
import java.awt.Graphics;

public class FillRect extends Shape {
    public FillRect() {
    };

    public FillRect(int x1, int y1, int x2, int y2, String name, Color color) {
        super(x1, y1, x2, y2, name, color);
    }

    public void drawShape(Graphics g) {
        g.setColor(color);
        g.fillRect(x1, y1, x2, y2);
    }

}
