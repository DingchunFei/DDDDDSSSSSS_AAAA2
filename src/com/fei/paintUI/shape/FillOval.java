package com.fei.paintUI.shape;


import java.awt.*;

public class FillOval extends Shape {
    public FillOval(){};
    public FillOval(int x1, int y1, int x2, int y2, String name, Color color, String text){
        super(x1,y1,x2,y2,name,color,text);
    }

    public void drawShape(Graphics g){
        g.setColor(color);
        g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

    }
}
