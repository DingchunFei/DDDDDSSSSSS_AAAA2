package com.fei.paintUI.util;

import com.fei.paintUI.shape.Shape;

import java.util.LinkedList;
import java.util.List;

public class ClientUtils {


    private static List<String> jsonMsg = new LinkedList<String>();

    private static List<Shape> shapes = new LinkedList<Shape>();

    //用来让UI放json数据
    public static List<String> getJsonMsg() {
        return jsonMsg;
    }

    public static void setJsonMsg(List<String> jsonMsg) {
        ClientUtils.jsonMsg = jsonMsg;
    }

    public static void sendMessage(Shape shape) {
        System.out.println("调用了！");
        shape.setRGB();         //设置一下三个分色
        jsonMsg.add(JacksonUtil.shape2Json(shape));
    }

    public static Boolean isEmpty(){
        if(shapes.size()==0){
            return true;
        }else{
            return false;
        }
    }

    public static String putShape(String jsonStr) {
        //每次只有一个
        Shape shape = JacksonUtil.json2Shape(jsonStr);

        List list = new LinkedList ();

        System.out.println(shape);

        if(shape.name.equals("Chatting")){
            //说明是聊天内容
            return shape.text;
        }else{
            shapes.add(shape);
            return null;
        }
    }

    public static Shape getShape() {
        System.out.println("getShape被调用了！");
        if(shapes.size()!=0) {
            return shapes.remove(0);
        }
        //每次只有一个
        return null;
    }
}
