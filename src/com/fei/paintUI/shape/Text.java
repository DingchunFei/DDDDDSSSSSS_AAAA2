package com.fei.paintUI.shape;

import java.awt.*;

public class Text extends Shape {
    public Text(){};
    public Text(int x1,int y1,int x2,int y2,String name,Color color,String text){
        super(x1,y1,x2,y2,name,color,text);
    }

    public void drawText(Graphics g){
        g.setColor(color);

        g.setFont(new Font("Arial",Font.BOLD,20));//设置字体
        if(text != null)
            ///System.out.println(super.toString());
            g.drawString( text, super.getX1(),super.getY1());

    }
}
