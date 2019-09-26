package com.fei.paintUI;


import com.fei.paintUI.shape.*;
import com.fei.paintUI.shape.Shape;
import com.fei.paintUI.util.ClientUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class DrawListener implements MouseListener, MouseMotionListener, ActionListener {

    private int x1, y1, x2, y2;
    private String name;
    private Color color;
    private Graphics g;
    private Shape[] shapeArray;
    private int index = 0;
    private String text;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public com.fei.paintUI.shape.Shape[] getShapeArray() {
        return shapeArray;
    }

    public void setShapeArray(com.fei.paintUI.shape.Shape[] shapeArray) {
        this.shapeArray = shapeArray;
    }

    // 初始化画笔
    public void setGr(Graphics g) {
        this.g = g;
    }

    // 初始化图形数组
    public void setSp(com.fei.paintUI.shape.Shape[] shapeArray) {
        this.shapeArray = shapeArray;
    }

    //鼠标点击
    public void mouseClicked(MouseEvent e) {
    }
    //鼠标按下
    public void mousePressed(MouseEvent e) {
        {
            x1 = e.getX();
            y1 = e.getY();
        }
    }
    //鼠标释放
    public void mouseReleased(MouseEvent e) {

        if(color==null){
            color=color.BLACK;
        }
        x2 = e.getX();
        y2 = e.getY();
        // 绘制直线
        if ("Line".equals(name)) {
            g.drawLine(x1, y1, x2, y2);
            Shape line = new Line(x1, y1, x2, y2, name, color,null);
            shapeArray[index++] = line;
            ClientUtils.sendMessage(line);
            //System.out.println("Line"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }
        if ("Rectangle".equals(name)) {
            g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
            Shape rect = new Rect(x1, y1, x2, y2, name, color,null);
            shapeArray[index++] = rect;
            ClientUtils.sendMessage(rect);

            //System.out.println("Rectangle"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }
        if ("FillRect".equals(name)) {
            g.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1),0,0);
            Shape fillRect = new FillRect(x1, y1, x2, y2, name, color,null);
            shapeArray[index++] = fillRect;
            ClientUtils.sendMessage(fillRect);

            //System.out.println("Rectangle"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }

        if ("Oval".equals(name)) {
            g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
            Shape oval = new Oval(x1, y1, x2, y2, name, color,null);
            shapeArray[index++] = oval;
            ClientUtils.sendMessage(oval);

            //System.out.println("Oval"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }
        if ("FillOval".equals(name)) {
            g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
            Shape fillOval = new FillOval(x1, y1, x2, y2, name, color,null);
            shapeArray[index++] = fillOval;
            ClientUtils.sendMessage(fillOval);

            //System.out.println("Oval"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }

        if ("Circle".equals(name)) {
            g.drawOval(x1, y1, Math.max(Math.abs(x2 - x1),Math.abs(y2 - y1)), Math.max(Math.abs(x2 - x1),Math.abs(y2 - y1)));
            Shape oval = new Oval(x1, y1, x1 + Math.max(Math.abs(x2 - x1),Math.abs(y2 - y1)), y1 + Math.max(Math.abs(x2 - x1),Math.abs(y2 - y1)), name, color,null);
            shapeArray[index++] = oval;
            ClientUtils.sendMessage(oval);

            //System.out.println("Oval"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }


        if ("Text".equals(name)) {

            String input = JOptionPane.showInputDialog("请输入你要写入的文字！");

            g.drawString(input,x1, y1);
            Shape text = new Text(x1, y1, x2, y2, name, color,input);
            shapeArray[index++] = text;
            ClientUtils.sendMessage(text);

        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    //鼠标拖动
    public void mouseDragged(MouseEvent e) {
        // 画笔重载需注意内存
        if ("Brush".equals(name)) {
            x2 = e.getX();
            y2 = e.getY();
            g.drawLine(x1, y1, x2, y2);
            Shape line = new Line(x1, y1, x2, y2, name, color,null);
            shapeArray[index++] = line;
            ClientUtils.sendMessage(line);
            x1 = x2;
            y1 = y2;
            //System.out.println("Line"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }
        if ("Rubber".equals(name)) {
            color = Color.white;
            g.setColor(color);
            //设置线宽
            ((Graphics2D) g).setStroke(new BasicStroke(20));
            x2 = e.getX();
            y2 = e.getY();
            g.drawLine(x1, y1, x2, y2);
            Shape eraser = new Eraser(x1, y1, x2, y2, name, color,null);
            shapeArray[index++] = eraser;
            ClientUtils.sendMessage(eraser);
            x1 = x2;
            y1 = y2;
            color = Color.black;
            g.setColor(color);
            ((Graphics2D) g).setStroke(new BasicStroke(1));
            //System.out.println("Rubber"+"    "+x1+"    "+ y1+ "    "+ x2+"    "+y2 +"    "+name+"    "+color);
        }
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void actionPerformed(ActionEvent e) {
        //如果是颜色选择就进入这个方法
        if ("".equals(e.getActionCommand())) {
            // 获取当前事件源，并强制转换
            JButton jb = (JButton) e.getSource();
            // 将按钮背景色赋值给color
            color = jb.getBackground();
            // 设置画笔背景色
            // 注意：不能直接写成g.setColor(jb.getBackground());后面重绘时需用到color参数；
            g.setColor(color);
            System.out.println(color);
        } else {
            name = e.getActionCommand();

            if ("Clear".equals(name)) {


                //清空画板
                for(int i=0;i<index;i++){
                    shapeArray[i]=null;
                }
                index=0;


                color = Color.white;
                g.setColor(color);
                x1 = 0;
                y1 = 0;
                x2 = 900;
                y2 = 700;
                g.fillRect(x1, y1, x2, y2);

                Shape fillrect = new FillRect(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = fillrect;

                ClientUtils.sendMessage(fillrect);

                color = Color.black;
                g.setColor(color);
            }

        }
    }

    public void rebuild(Shape[] shapes){

        for (Shape shape:shapes) {
            x1=shape.getX1();
            y1=shape.getY1();
            x2=shape.getX2();
            y2=shape.getY2();
            name = shape.getName();
            text = shape.getText();
            color = shape.getColor();

            g.setColor(color);

            if ("Line".equals(shape.getName())) {
                g.drawLine(x1, y1, x2, y2);
                Shape line = new Line(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = line;
                ClientUtils.sendMessage(line);

            }else if ("Rectangle".equals(shape.getName())) {
                g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                Shape rect = new Rect(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = rect;
                ClientUtils.sendMessage(rect);

            }else if ("FillRect".equals(shape.getName())) {
                g.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1),0,0);
                Shape fillRect = new FillRect(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = fillRect;
                ClientUtils.sendMessage(fillRect);

            } else if ("Oval".equals(shape.getName())) {
                g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                Shape oval = new Oval(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = oval;
                ClientUtils.sendMessage(oval);

            }else if ("FillOval".equals(shape.getName())) {
                g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
                Shape oval = new Oval(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = oval;
                ClientUtils.sendMessage(oval);

            }else if ("Circle".equals(shape.getName())) {
                g.drawOval(x1, y1, x2, y2);
                Shape oval = new Oval(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = oval;
                ClientUtils.sendMessage(oval);

            }else if ("Brush".equals(shape.getName())) {
                g.drawLine(x1, y1, x2, y2);
                Shape line = new Line(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = line;
                ClientUtils.sendMessage(line);
            }else if ("Clear".equals(shape.getName())) {
                color = Color.white;
                g.setColor(color);
                g.fillRect(x1, y1, x2, y2);

                Shape fillrect = new FillRect(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = fillrect;

                ClientUtils.sendMessage(fillrect);
                color = Color.black;
                g.setColor(color);

            }else if ("Text".equals(shape.getName())) {

                g.drawString(text,x1, y1);
                Shape string = new Text(x1, y1, x2, y2, name, color,text);
                shapeArray[index++] = string;
                ClientUtils.sendMessage(string);

            }else if ("Rubber".equals(shape.getName())) {
                color = Color.white;
                g.setColor(color);
                //设置线宽
                ((Graphics2D) g).setStroke(new BasicStroke(20));

                g.drawLine(x1, y1, x2, y2);
                Shape eraser = new Eraser(x1, y1, x2, y2, name, color,null);
                shapeArray[index++] = eraser;
                ClientUtils.sendMessage(eraser);

                color = Color.black;
                g.setColor(color);
                ((Graphics2D) g).setStroke(new BasicStroke(1));

            }
        }
    }

    public void rebuild(Shape shape){

        x1=shape.getX1();
        y1=shape.getY1();
        x2=shape.getX2();
        y2=shape.getY2();
        text = shape.getText();
        name = shape.getName();

        color = shape.getColor();

        g.setColor(color);

        if ("Line".equals(shape.getName())) {
            g.drawLine(x1, y1, x2, y2);

        }else if ("Rectangle".equals(shape.getName())) {
            g.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

        }else if ("FillRect".equals(shape.getName())) {
            g.fillRoundRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1),0,0);

        }else if ("Oval".equals(shape.getName())) {
            g.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

        }else if ("FillOval".equals(shape.getName())) {
            g.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));

        }else if ("Circle".equals(shape.getName())) {
            g.drawOval(x1, y1, x2, y2);
        }else if ("Brush".equals(shape.getName())) {
            g.drawLine(x1, y1, x2, y2);

        }else if ("Text".equals(shape.getName())) {
            g.drawString(text, x1, y1);

        }else if ("Clear".equals(shape.getName())) {
            color = Color.white;
            g.setColor(color);
            g.fillRect(x1, y1, x2, y2);
            color = Color.black;
            g.setColor(color);


        }else if ("Rubber".equals(shape.getName())) {
            color = Color.white;
            g.setColor(color);
            //设置线宽
            ((Graphics2D) g).setStroke(new BasicStroke(20));
            g.drawLine(x1, y1, x2, y2);

            color = Color.black;
            g.setColor(color);
            ((Graphics2D) g).setStroke(new BasicStroke(1));
        }
        shapeArray[index++] = shape;

    }


}
