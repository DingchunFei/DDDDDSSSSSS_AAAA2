package com.fei.paintUI.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fei.paintUI.shape.Shape;

import java.awt.*;
import java.io.IOException;

public class JacksonUtil {

    public static String shape2Json(Shape[] shapeArray){

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr =null;
        try {
            jsonStr = mapper.writeValueAsString(shapeArray);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


    public static Shape[] json2Shapes(String jsonStr) {

        ObjectMapper mapper = new ObjectMapper();
        Shape[] shapeArray =null;
        try {
            shapeArray = mapper.readValue(jsonStr, Shape[].class);

            //为每个图形重新设置颜色
            for(int i=0;i<shapeArray.length;i++){

                shapeArray[i].setColor(new Color(shapeArray[i].getRed(),shapeArray[i].getGreen(),shapeArray[i].getBlue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shapeArray;
    }

    public static Shape json2Shape(String jsonStr) {

        ObjectMapper mapper = new ObjectMapper();
        Shape shape = null;
        try {
            shape = mapper.readValue(jsonStr, Shape.class);
            shape.setColor(new Color(shape.getRed(),shape.getGreen(),shape.getBlue()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return shape;
    }

    public static String shape2Json(Shape shape){

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr =null;
        try {
            jsonStr = mapper.writeValueAsString(shape);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }


}
