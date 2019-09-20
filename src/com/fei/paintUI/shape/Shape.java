package com.fei.paintUI.shape;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.Color;
import java.awt.Graphics;




@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name", include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rect.class, name = "Rectangle"),
        @JsonSubTypes.Type(value = Line.class, name = "Line"),
        @JsonSubTypes.Type(value = Oval.class, name = "Oval"),
        @JsonSubTypes.Type(value = FillRect.class, name = "Clear"),
        @JsonSubTypes.Type(value = Eraser.class, name = "Rubber"),
        @JsonSubTypes.Type(value = Line.class, name = "Brush")
}
)
public abstract class Shape {
    public int x1, y1, x2, y2;
    public String name;
    @JsonIgnore
    public Color color;
    public Integer red;
    public Integer green;
    public Integer blue;

    public Shape() {
    };

    public Shape(int x1, int y1, int x2, int y2, String name, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.name = name;
        this.color = color;
    }

    public void setRGB(){
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
    }

    public void setColorByRGB(){
        this.color=new Color(color.getRed(), color.getGreen(),color.getBlue());
        System.out.println(this.color);
    }


    public void drawShape(Graphics g) {

    }

    @Override
    public String toString() {
        return "Shape{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", name='" + name + '\'' +
                ", color=" + color +
                '}';
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
/*        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();*/
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }
}
