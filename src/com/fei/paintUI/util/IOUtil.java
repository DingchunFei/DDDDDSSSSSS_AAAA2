package com.fei.paintUI.util;

import com.fei.paintUI.DrawListener;
import com.fei.paintUI.shape.Shape;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class IOUtil {

    public static Shape[] readFile(File file){//读文件
        BufferedReader bReader;
        Shape[] tempArray =null;
        try {
            bReader=new BufferedReader(new FileReader(file));
            StringBuffer sBuffer=new StringBuffer();
            String str;
            while((str=bReader.readLine())!=null){
                sBuffer.append(str+'\n');
                //System.out.println(str);
                tempArray = JacksonUtil.json2Shapes(str);
            }

            for(int i=0;i<tempArray.length;i++){
                //把颜色重新设置回去
                tempArray[i].setColorByRGB();
                System.out.println(tempArray[i]);
            }

            //textArea.setText(sBuffer.toString());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return tempArray;
    }

    //菜单栏读取文件
    public static void writeFile(String savepath, DrawListener dl){//写文件
        FileOutputStream fos= null;


        Shape [] tempArray = new Shape[dl.getIndex()];

        //数组赋值
        for(int i=0;i<dl.getIndex();i++){
            tempArray[i] = dl.getShapeArray()[i];
            System.out.println(tempArray[i]);
            tempArray[i].setRGB();
        }

        String jsonStr = JacksonUtil.shape2Json(tempArray);

        try {
            fos=new FileOutputStream(savepath);
            fos.write(jsonStr.getBytes());
            fos.close();
            System.out.println("save success！");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
